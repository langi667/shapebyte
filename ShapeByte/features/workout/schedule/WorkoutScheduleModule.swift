//
//  WorkoutScheduleModule.swift
//  ShapeByte
//
//  Created by Lang, Stefan [RTL Tech] on 02.08.24.
//

import Foundation

struct WorkoutScheduleModule {
    private static let diModule = DIModule()

    static func currentWorkoutScheduleEntryUseCase() -> CurrentWorkoutScheduleEntryUseCase {
        CurrentWorkoutScheduleEntryUseCase(repository: workoutScheduleRepository)
    }

    private static let workoutScheduleRepository: WorkoutScheduleRepository = diModule.instanceTypeOrCreate(
        type: WorkoutScheduleRepository.self,
        create: {
            WorkoutScheduleRepository(datasource: workoutScheduleDatasource)
        }
    )

    private static let workoutScheduleDatasource: WorkoutScheduleDatasource = diModule.instanceTypeOrCreate(
        name: "WorkoutScheduleDatasource",
        create: {
            WorkoutScheduleDatasourceMock()
        }
    )

    private init() {}
}
