//
//  HomeModule.swift
//  ShapeByte
//
//  Created by Lang, Stefan [Shape Byte Tech] on 02.08.24.
//

import Foundation

struct HomeModule {
    private static let diModule: DIModule = DIModule()

    static func homeRootViewModel() -> HomeRootViewModel {
        return HomeRootViewModel(
            currentWorkoutScheduleEntryUseCase: WorkoutScheduleModule.currentWorkoutScheduleEntryUseCase(),
            recentHistoryUseCase: WorkoutHistoryModule.recentHistoryUseCase()
        )
    }

    static func homeCoordinator() -> HomeCoordinator {
        return diModule.instanceTypeOrCreate(
            type: HomeCoordinator.self,
            create: {
                HomeCoordinator()
            }
        )
    }
}
