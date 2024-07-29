//
//  ExerciseSetsCoordinator.swift
//  ShapeByte
//
//  Created by Lang, Stefan [RTL Tech] on 29.07.24.
//

import Foundation
import Combine

class ExerciseSetsCoordinator {
    fileprivate (set) var statePublisher: CurrentValueSubject<ExerciseSetsState, Never> = CurrentValueSubject<ExerciseSetsState, Never>(.idle)

    private var currSetCoordinator: (any ExerciseSetCoordinating)?
    private var sets: [ExerciseSet] = []
    private var statePublisherSink: AnyCancellable?
    private var currSetIndex: Int = -1

    func start(sets: [ExerciseSet]) {
        if self.statePublisher.value != .idle || self.statePublisher.value == .finished {
            return
        } // TODO: clear data

        self.sets = sets
        // TODO: check if empty

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

        self.currSetCoordinator = startCoordinationFor(nextSet)
        currSetIndex = nextSetIndex

        self.statePublisherSink = currSetCoordinator!.statePublisher.sink { state in
            self.handleSetStateReceived(state)
        }
    }

    private func handleSetStateReceived(_ state: ExerciseSetState) {
        let nextSetIndex = currSetIndex + 1

        switch state {
        case .idle:
            break // TODO: handle
        case .running(let setData):
            self.statePublisher.value = ExerciseSetsState.running(
                currentSet: currSetIndex,
                totalSets: sets.count,
                currentSetProgress: setData.progress,
                totalProgress: totalProgress(currentSetProgress: setData.progress)
            )
        case .paused(let setData):
            self.statePublisher.value = ExerciseSetsState.paused(
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
        print("set: \(currSetIndex), progress: \(retVal.absoluteValue)")
        return retVal
    }

    private func finish() {
        statePublisher.value = .finished
    }

    private func startCoordinationFor(_ exerciseSet: ExerciseSet) -> any ExerciseSetCoordinating {
        let retVal: any ExerciseSetCoordinating

        switch exerciseSet {
        case .timed:
            retVal = ExerciseSetModule.shared.timedSetCoordinator
        default:
            // TODO: log fallback
            retVal = ExerciseSetModule.shared.defaultSetCoordinator
        }

        retVal.start(set: exerciseSet)
        return retVal

    }
}
