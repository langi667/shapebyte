//
//  ExerciseSetsModule.swift
//  ShapeByte
//
//  Created by Lang, Stefan [Shape Byte Tech] on 29.07.24.
//

import Foundation

class ExerciseSetsModule {
    static private let module = BaseModule()

    static func timedExerciseSetsViewModel() -> TimedExerciseSetsViewModel {
        return TimedExerciseSetsViewModel(
            logger: DefaultLogger()
        )
    }

    static let setsCoordinator: ExerciseSetsCoordinator = module.instanceTypeOrCreate(
        type: ExerciseSetsCoordinator.self,
        create: {
            ExerciseSetsCoordinator(logger: DefaultLogger())
        }
    )

    private init() {}
}
