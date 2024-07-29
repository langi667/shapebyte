//
//  WorkoutUIModule.swift
//  ShapeByte
//
//  Created by Lang, Stefan [RTL Tech] on 28.07.24.
//

import Foundation

class ExerciseSetModule: BaseModule {
    static let shared = ExerciseSetModule()

    lazy var timedSetCoordinator: some ExerciseSetCoordinating = instanceTypeOrCreate(
        name: "TimedSetCoordinating",
        create: {
            TimedExerciseSetCoordinator()
        }
    )

    lazy var defaultSetCoordinator: some ExerciseSetCoordinating = instanceTypeOrCreate(
        name: "DefaultExerciseSetCoordinating",
        create: {
            DefaultExerciseSetCoordinator()
        }
    )

    private override init() {}
}
