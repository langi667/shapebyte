//
//  WorkoutUIModule.swift
//  ShapeByte
//
//  Created by Lang, Stefan [Shape Byte Tech] on 28.07.24.
//

import Foundation

class ExerciseSetModule {
    static let module = BaseModule()

    static let timedSetCoordinator: some ExerciseSetCoordinating = module.instanceTypeOrCreate(
        name: "TimedSetCoordinating",
        create: {
            TimedExerciseSetCoordinator()
        }
    )

    static let defaultSetCoordinator: some ExerciseSetCoordinating = module.instanceTypeOrCreate(
        name: "DefaultExerciseSetCoordinating",
        create: {
            DefaultExerciseSetCoordinator()
        }
    )
}
