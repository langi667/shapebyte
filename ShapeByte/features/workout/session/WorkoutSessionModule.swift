//
//  WorkoutModule.swift
//  ShapeByte
//
//  Created by Lang, Stefan [Shape Byte Tech] on 31.07.24.
//

import Foundation

class WorkoutSessionModule {
    private static let diModule = DIModule()

    static func workoutSessionCoordinator() -> WorkoutSessionCoordinator { diModule.instanceTypeOrCreate(
        type: WorkoutSessionCoordinator.self,
        create: {
            WorkoutSessionCoordinator(
                workoutSessionUseCase: Self.currentWorkoutSessionUseCase(),
                logger: SharedModule.logger
            )
        }
    )}

    static func currentWorkoutSessionUseCase() -> CurrentWorkoutSessionUseCase {
        return diModule.instanceTypeOrCreate(
            type: CurrentWorkoutSessionUseCase.self,
            create: {
                CurrentWorkoutSessionUseCase(repository: Self.workoutSessionRepository())
            }
        )
    }

    private static func workoutSessionRepository() -> WorkoutSessionRepository {
        return diModule.instanceTypeOrCreate(
            type: WorkoutSessionRepository.self,
            create: {
                WorkoutSessionRepository(datasource: Self.workoutDatasource())
            }
        )
    }

    private static func workoutDatasource() -> any WorkoutSessionDatasource {
        return  diModule.instanceTypeOrCreate(
            type: WorkoutSessionDatasource.self,
            create: {
                WorkoutSessionDatasourceMock()
            }
        )
    }
}
