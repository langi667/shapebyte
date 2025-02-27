package de.stefan.lang.shapebyte.features.workout.workoutData

import de.stefan.lang.coreutils.di.DIModuleDeclaration
import de.stefan.lang.shapebyte.features.workout.workoutData.mocks.QuickWorkoutsDatasourceMocks
import de.stefan.lang.shapebyte.features.workout.workoutData.mocks.WorkoutHistoryDataSource
import de.stefan.lang.shapebyte.features.workout.workoutData.mocks.WorkoutHistoryDataSourceMocks
import de.stefan.lang.shapebyte.features.workout.workoutData.mocks.WorkoutHistoryRepository
import de.stefan.lang.shapebyte.features.workout.workoutData.mocks.WorkoutScheduleDatasource
import de.stefan.lang.shapebyte.features.workout.workoutData.mocks.WorkoutScheduleDatasourceMock
import de.stefan.lang.shapebyte.features.workout.workoutData.mocks.WorkoutScheduleRepository
import de.stefan.lang.shapebyte.features.workout.workoutData.workout.QuickWorkoutsDatasource
import de.stefan.lang.shapebyte.features.workout.workoutData.workout.QuickWorkoutsRepository
import org.koin.core.component.get

interface WorkoutDataModuleProviding {
    fun workoutHistoryRepository(): WorkoutHistoryRepository
    fun workoutScheduleRepository(): WorkoutScheduleRepository
}

object WorkoutDataModule :
    DIModuleDeclaration(
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

    ),
    WorkoutDataModuleProviding {
    override fun workoutHistoryRepository(): WorkoutHistoryRepository = get()
    override fun workoutScheduleRepository(): WorkoutScheduleRepository = get()
}
