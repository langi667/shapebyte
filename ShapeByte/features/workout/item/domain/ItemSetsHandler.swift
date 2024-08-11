//
//  ItemSetsHandler.swift
//  ShapeByte
//
//  Created by Lang, Stefan [Shape Byte Tech] on 29.07.24.
//

import Foundation
import Combine

class ItemSetsHandler: ObservableObject {
    @Published var state: ItemSetsState = .idle

    private let logger: Logging
    private var currSetHandler: (any ItemSetHandling)?
    private var sets: [ItemSet] = []
    private var statePublisherSink: AnyCancellable?
    private var currSetIndex: Int = -1

    private let timedSetHandler: ItemSetHandling
    private let defaultSetHandler: ItemSetHandling

    init(logger: Logging,
         timedSetHandler: ItemSetHandling,
         defaultSetHandler: ItemSetHandling
    ) {
        self.logger = logger
        self.timedSetHandler = timedSetHandler
        self.defaultSetHandler = defaultSetHandler
    }

    func start(sets: [ItemSet]) {
        if self.sets == sets {
            return
        }

        self.sets = sets
        currSetIndex = -1

        self.state = .started(totalSets: sets.count)
        startNextSet()
    }

    private func startNextSet() {
        let nextSetIndex = currSetIndex + 1

        if nextSetIndex == sets.count {
            finish()
            return
        }

        let nextSet = sets[nextSetIndex]
        currSetIndex = nextSetIndex

        startCoordinationFor(nextSet, onReceivedState: handleSetStateReceived(_:))
    }

    private func handleSetStateReceived(_ state: ItemSet.State) {
        let nextSetIndex = currSetIndex + 1

        switch state {
        case .idle:
            break // TODO: handle
            case .started(let setData):
            logger.logDebug("Started set \(currSetIndex)")
            self.state = ItemSetsState.running(
                currentSet: currSetIndex,
                totalSets: sets.count,
                currentSetProgress: Progress(0),
                totalProgress: totalProgress(currentSetProgress: Progress(0)),
                setData: setData
            )
        case .running(let setData):
            self.state = ItemSetsState.running(
                currentSet: currSetIndex,
                totalSets: sets.count,
                currentSetProgress: setData.progress,
                totalProgress: totalProgress(currentSetProgress: setData.progress),
                setData: setData
            )
        case .paused(let setData):
            self.state = ItemSetsState.paused

        case .finished:
            if nextSetIndex >= sets.count {
                finish()
            } else {
                startNextSet()
            }
        }
    }

    private func totalProgress(currentSetProgress: Progress) -> Progress {
        if currSetIndex < 0 {
            return .zero
        }

        let total = CGFloat((sets.count) * 100)
        let processedSets = CGFloat(currSetIndex) * 100

        let totalProgress = processedSets  + CGFloat(currentSetProgress.absoluteValue)
        let totalProgressRelative = totalProgress / total

        let retVal: Progress = Progress(totalProgressRelative)
        return retVal
    }

    private func finish() {
        self.statePublisherSink?.cancel()
        self.statePublisherSink = nil
        self.currSetHandler = nil

        self.currSetIndex = -1
        self.sets = []

        state = .finished
    }

    private func startCoordinationFor(
        _ itemSet: ItemSet,
        onReceivedState: @escaping (ItemSet.State) -> Void
    ) {
        let nextHandler: any ItemSetHandling

        switch itemSet {
        case .timed:
            nextHandler = timedSetHandler
        default:
            // TODO: log fallback
            nextHandler = defaultSetHandler
        }

        if self.currSetHandler !== nextHandler {
            self.statePublisherSink?.cancel()
            self.statePublisherSink = nil

            self.currSetHandler = nextHandler
            self.statePublisherSink = nextHandler.statePublisher
                .removeDuplicates()
                .sink { state in
                    onReceivedState(state)
            }
        }

        nextHandler.start(set: itemSet)
    }
}
