package de.stefan.lang.shapebyte.features.workout.workoutData

import de.stefan.lang.core.di.RootModule
import de.stefan.lang.foundationCore.FoundationCoreModule
import de.stefan.lang.shapebyte.features.workout.workoutData.mocks.QuickWorkoutsDatasourceMocks
import de.stefan.lang.shapebyte.features.workout.workoutData.mocks.WorkoutHistoryDataSource
import de.stefan.lang.shapebyte.features.workout.workoutData.mocks.WorkoutHistoryDataSourceMocks
import de.stefan.lang.shapebyte.features.workout.workoutData.mocks.WorkoutHistoryRepository
import de.stefan.lang.shapebyte.features.workout.workoutData.mocks.WorkoutScheduleDatasource
import de.stefan.lang.shapebyte.features.workout.workoutData.mocks.WorkoutScheduleDatasourceMock
import de.stefan.lang.shapebyte.features.workout.workoutData.mocks.WorkoutScheduleRepository
import de.stefan.lang.shapebyte.features.workout.workoutData.workout.QuickWorkoutsDatasource
import de.stefan.lang.shapebyte.features.workout.workoutData.workout.QuickWorkoutsRepository

object WorkoutDataModule :
    RootModule(
        allEnvironments = {
            single<WorkoutHistoryRepository> { WorkoutHistoryRepository(dataSource = get()) }
            single<WorkoutScheduleRepository> { WorkoutScheduleRepository(datasource = get()) }

            // TODO: change once implemented !!!
            single<QuickWorkoutsDatasource> { QuickWorkoutsDatasourceMocks() }
            single<QuickWorkoutsRepository> {
                QuickWorkoutsRepository(
                    dataSource = get(),
                    logger = get(),
                )
            }
        },
        appEnvironmentOnly = {
            single<WorkoutHistoryDataSource> { WorkoutHistoryDataSourceMocks } // TODO: change once implemented !!!
            single<WorkoutScheduleDatasource> { WorkoutScheduleDatasourceMock } // TODO: change once implemented !!!
        },
        testEnvironmentOnly = {
            single<WorkoutHistoryDataSource> { WorkoutHistoryDataSourceMocks }
            single<WorkoutScheduleDatasource> { WorkoutScheduleDatasourceMock }
        },
        dependencies = listOf(FoundationCoreModule),
    )
