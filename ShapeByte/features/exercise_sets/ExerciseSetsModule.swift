//
//  ExerciseSetsModule.swift
//  ShapeByte
//
//  Created by Lang, Stefan [RTL Tech] on 29.07.24.
//

import Foundation

class ExerciseSetsModule: BaseModule {
    static let shared = ExerciseSetsModule()

    lazy var setsCoordinator: ExerciseSetsCoordinator = instanceTypeOrCreate(
        type: ExerciseSetsCoordinator.self,
        create: {
            ExerciseSetsCoordinator()
        }
    )

    private override init() {}
}
