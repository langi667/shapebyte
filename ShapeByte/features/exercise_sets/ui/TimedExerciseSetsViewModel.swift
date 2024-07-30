//
//  TimedSetViewModel.swift
//  ShapeByte
//
//  Created by Lang, Stefan [RTL Tech] on 27.07.24.
//

import Foundation
import Combine

class TimedExerciseSetsViewModel: ObservableObject {
    fileprivate (set) var exerciseSets: ExerciseSets = .empty

    @Published var numberOfSets: Int = 0
    @Published var currentSetProgress: Progress = .zero
    @Published var currentSetElapsedTime: Int = 1
    @Published var workoutProgress: Progress = Progress(0)
    @Published var currentSetIndex: Int = 0
    @Published var fadeOutOpacity: CGFloat = 0.0

    private var workoutTimer: AnyCancellable?
    private let setsCoordinator: ExerciseSetsCoordinator = ExerciseSetsModule.setsCoordinator
    private var cancelables: Set<AnyCancellable> = Set<AnyCancellable>()
    private let logger: Logging

    init(
        exerciseSets: ExerciseSets,
        logger: Logging
    ) {
        self.exerciseSets = exerciseSets
        self.numberOfSets = exerciseSets.count
        self.logger = logger
    }

    func startWorkout() {
        setsCoordinator.statePublisher.sink { state in
            self.handleStateChanged(state)
        }.store(in: &cancelables) // TODO: clear cancelables

        self.numberOfSets = exerciseSets.count

        setsCoordinator.start(
            sets: exerciseSets.sets
        )
    }

    func stopWorkoutTimer() {
        workoutTimer?.cancel()
        workoutTimer = nil
    }

    private func handleStateChanged(_ state: ExerciseSetsState) {
        switch state {
        case .idle:
            break
        case .running(let setIndex, _, let currentSetProgress, let totalProgress):
            if let setDuration = self.exerciseSets.exerciseSetFor(index: setIndex)?.duration {
                let elapsed = ((1.0 - currentSetProgress.value) * setDuration)
                self.currentSetIndex = setIndex

                if elapsed > 0 {
                    currentSetElapsedTime = Int(elapsed.rounded(.up))
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
