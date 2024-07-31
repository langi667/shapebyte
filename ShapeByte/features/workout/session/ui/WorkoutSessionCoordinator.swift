//
//  WorkoutSessionCoordinator.swift
//  ShapeByte
//
//  Created by Lang, Stefan [RTL Tech] on 31.07.24.
//

import Foundation

class WorkoutSessionCoordinator: ViewModel, ObservableObject {

    // TODO: add loading, paused, stopped, finished ...
    enum State {
        case runningTimedExercises
    }

    let workoutSessionUseCase: CurrentWorkoutSessionUseCase
    let timedExerciseSetsViewModel: TimedExerciseSetsViewModel

    @Published var state: State = .runningTimedExercises
    @Published var session: WorkoutSession = .empty

    init(
        workoutSessionUseCase: CurrentWorkoutSessionUseCase, 
        timedExerciseSetsViewModel: TimedExerciseSetsViewModel
    ) {
        self.workoutSessionUseCase = workoutSessionUseCase
        self.timedExerciseSetsViewModel = timedExerciseSetsViewModel
    }

    func onViewAppeared() {
        updateData()
        start()
    }

    func updateData() {
        if let workoutSession = workoutSessionUseCase.invoke() {
            self.session = workoutSession
        } else {
            self.session = .empty // TODO: clear
        }
    }

    func start() {
        // TODO: implement correctly
        switch state {
        case .runningTimedExercises:
            timedExerciseSetsViewModel.startWorkouWith(self.session.exercises)
        }
    }
}
