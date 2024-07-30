//
//  ExerciseSetsModule.swift
//  ShapeByte
//
//  Created by Lang, Stefan [RTL Tech] on 29.07.24.
//

import Foundation

class ExerciseSetsModule {
    static private let module = BaseModule()

    static var setsCoordinator: ExerciseSetsCoordinator = module.instanceTypeOrCreate(
        type: ExerciseSetsCoordinator.self,
        create: {
            ExerciseSetsCoordinator(logger: DefaultLogger())
        }
    )

    static func timedExerciseSetsViewModel(with exerciseSets: ExerciseSets) -> TimedExerciseSetsViewModel {
        TimedExerciseSetsViewModel(
            exerciseSets: exerciseSets,
            logger: DefaultLogger()
        )
    }

    private init() {}
}
