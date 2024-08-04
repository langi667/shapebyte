//
//  WorkoutHistoryModule.swift
//  ShapeByte
//
//  Created by Lang, Stefan [Shape Byte Tech] on 04.08.24.
//

import Foundation

struct WorkoutHistoryModule {
    private static let diModule = DIModule()

    static func recentHistoryUseCase() -> RecentWorkoutHistoryUseCase {
        RecentWorkoutHistoryUseCase(repository: repository)
    }

    private static let repository: WorkoutHistoryRepository = diModule.instanceTypeOrCreate(
        type: WorkoutHistoryRepository.self,
        create: {
            WorkoutHistoryRepository(dataSource: datasource)
        }
    )

    private static let datasource: any WorkoutHistoryDataSource = diModule.instanceTypeOrCreate(
        type: WorkoutHistoryDataSource.self,
        create: {
            WorkoutHistoryDataSourceMocks()
        }
    )

    private init () {}
}
