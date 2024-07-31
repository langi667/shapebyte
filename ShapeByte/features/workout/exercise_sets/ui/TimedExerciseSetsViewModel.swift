//
//  TimedSetViewModel.swift
//  ShapeByte
//
//  Created by Lang, Stefan [Shape Byte Tech] on 27.07.24.
//

import Foundation
import Combine

class TimedExerciseSetsViewModel: ObservableObject {
    fileprivate (set) var exerciseSets: ExerciseSets = .empty

    @Published var numberOfSets: Int = 0
    @Published var currentSetProgress: Progress = .zero
    @Published var currentSetElapsedTime: Int = 1
    @Published var currentSetDuration: DurationWrapper = .invalid

    @Published var workoutProgress: Progress = Progress(0)
    @Published var currentSetIndex: Int = -1
    @Published var fadeOutOpacity: CGFloat = 0.0

    @Published var currentSetElapsedTimeText: String = ""

    private let setsCoordinator: ExerciseSetsCoordinator = ExerciseSetsModule.setsCoordinator
    private var cancelables: Set<AnyCancellable> = Set<AnyCancellable>()
    private let logger: Logging

    init(
        logger: Logging
    ) {
        self.logger = logger
    }

    func startWorkouWith(_ sets: ExerciseSets) {
        stopWorkout()

        self.exerciseSets = sets
        self.numberOfSets = exerciseSets.count

        startWorkout()
    }

    func startWorkout() {
        stopWorkout()

        setsCoordinator.statePublisher.sink { state in
            self.handleStateChanged(state)
        }.store(in: &cancelables)

        self.numberOfSets = exerciseSets.count
        
        setsCoordinator.start(
            sets: exerciseSets.sets
        )
    }

    func stopWorkout() {
        cancelables.removeAll()
    }

    private func handleStateChanged(_ state: ExerciseSetsState) {
        switch state {
        case .idle:
            break
        case .running(let setIndex, _, let currentSetProgress, let totalProgress):
            if let setDuration = self.exerciseSets.exerciseSetFor(index: setIndex)?.duration {
                let lastSetIndex = currentSetIndex

                if lastSetIndex != setIndex {
                    self.currentSetDuration = DurationWrapper(setDuration)
                }

                let elapsed = ((1.0 - currentSetProgress.value) * setDuration)
                self.currentSetIndex = setIndex

                if elapsed > 0 {
                    currentSetElapsedTime = Int(elapsed.rounded(.up))
                    currentSetElapsedTimeText = DurationFormatter.secondsToString(currentSetElapsedTime)
                }
            }

            self.currentSetProgress = currentSetProgress
            self.workoutProgress = totalProgress

        case .paused:
            break // TODO: handle
        case .finished:
           break

        }
    }
}
