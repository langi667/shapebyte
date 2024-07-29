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
    @Published var workoutElapsedTime: CGFloat = 0
    @Published var workoutProgress: Progress = Progress(0)

    private var workoutTimer: AnyCancellable?
    private let setsCoordinator: ExerciseSetsCoordinator = ExerciseSetsModule.shared.setsCoordinator
    private var cancelables: Set<AnyCancellable> = Set<AnyCancellable>()

    init(
        exerciseSets: ExerciseSets
    ) {
        self.exerciseSets = exerciseSets
        self.numberOfSets = exerciseSets.count
    }

    func startWorkout() {
        setsCoordinator.statePublisher.sink { state in
            switch state {
            case .idle:
                break // TODO: handle
            case .running(_, _, let currentSetProgress, let totalProgress):
                self.currentSetProgress = currentSetProgress
                self.workoutProgress = totalProgress
            case .paused:
                break // TODO: handle
            case .finished:
                break // TODO: handle
            }
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
}
