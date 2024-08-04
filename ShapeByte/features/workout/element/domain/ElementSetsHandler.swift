//
//  ElementSetsHandler.swift
//  ShapeByte
//
//  Created by Lang, Stefan [Shape Byte Tech] on 29.07.24.
//

import Foundation
import Combine

class ElementSetsHandler: ObservableObject {
    @Published var state: ElementSetsUIState = .idle

    private let logger: Logging
    private var currSetHandler: (any ElementSetHandling)?
    private var sets: [ElementSet] = []
    private var statePublisherSink: AnyCancellable?
    private var currSetIndex: Int = -1

    private let timedSetHandler: ElementSetHandling
    private let defaultSetHandler: ElementSetHandling

    init(logger: Logging,
         timedSetHandler: ElementSetHandling,
         defaultSetHandler: ElementSetHandling
    ) {
        self.logger = logger
        self.timedSetHandler = timedSetHandler
        self.defaultSetHandler = defaultSetHandler
    }

    func start(sets: [ElementSet]) {
        if self.sets == sets {
            return
        }

        self.state = .idle

        self.sets = sets
        currSetIndex = -1
        startNextSet()
    }

    private func startNextSet() {
        let nextSetIndex = currSetIndex + 1

        if nextSetIndex == sets.count {
            finish()
            return
        }

        let nextSet = sets[nextSetIndex]

        statePublisherSink?.cancel()
        statePublisherSink = nil

        self.currSetHandler = startCoordinationFor(nextSet)
        currSetIndex = nextSetIndex

        self.statePublisherSink = currSetHandler?.statePublisher
            .sink { state in
                self.handleSetStateReceived(state)
        }
    }

    private func handleSetStateReceived(_ state: ElementSet.State) {
        let nextSetIndex = currSetIndex + 1

        switch state {
        case .idle:
            break // TODO: handle
        case .running(let setData):
            self.state = ElementSetsUIState.running(
                currentSet: currSetIndex,
                totalSets: sets.count,
                currentSetProgress: setData.progress,
                totalProgress: totalProgress(currentSetProgress: setData.progress)
            )
        case .paused(let setData):
            self.state = ElementSetsUIState.paused(
                currentSet: currSetIndex,
                totalSets: sets.count,
                currentSetProgress: setData.progress,
                totalProgress: totalProgress(currentSetProgress: setData.progress)
            )

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
        state = .finished
    }

    private func startCoordinationFor(_ elementSet: ElementSet) -> any ElementSetHandling {
        let retVal: any ElementSetHandling

        switch elementSet {
        case .timed:
            retVal = timedSetHandler
        default:
            // TODO: log fallback
            retVal = defaultSetHandler
        }

        retVal.start(set: elementSet)
        return retVal

    }
}
