//
//  WorkoutModule.swift
//  ShapeByte
//
//  Created by Lang, Stefan [RTL Tech] on 31.07.24.
//

import Foundation

class WorkoutSessionModule {
    static let module = BaseModule()

    static func workoutSessionCoordinator() -> WorkoutSessionCoordinator { module.instanceTypeOrCreate(
        type: WorkoutSessionCoordinator.self,
        create: {
            WorkoutSessionCoordinator(
                workoutSessionUseCase: Self.currentWorkoutSessionUseCase(),
                timedExerciseSetsViewModel: ExerciseSetsModule.timedExerciseSetsViewModel()
            )
        }
    )}

    static func currentWorkoutSessionUseCase() -> CurrentWorkoutSessionUseCase {
        return module.instanceTypeOrCreate(
            type: CurrentWorkoutSessionUseCase.self,
            create: {
                CurrentWorkoutSessionUseCase(repository: Self.workoutSessionRepository())
            }
        )
    }

    private static func workoutSessionRepository() -> WorkoutSessionRepository {
        return module.instanceTypeOrCreate(
            type: WorkoutSessionRepository.self,
            create: {
                WorkoutSessionRepository(datasource: Self.workoutDatasource())
            }
        )
    }

    private static func workoutDatasource() -> any WorkoutSessionDatasource {
        return  module.instanceTypeOrCreate(
            type: WorkoutSessionDatasource.self,
            create: {
                WorkoutSessionDatasourceMock()
            }
        )
    }
}
