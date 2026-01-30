package de.stefan.lang.shapebyte.features.workout.data

import de.stefan.lang.core.di.RootModule
import de.stefan.lang.foundationCore.FoundationCoreModule
import de.stefan.lang.shapebyte.features.workout.data.contract.WorkoutDataContract
import de.stefan.lang.shapebyte.features.workout.data.contract.history.WorkoutHistoryDataSource
import de.stefan.lang.shapebyte.features.workout.data.contract.history.WorkoutHistoryEntry
import de.stefan.lang.shapebyte.features.workout.data.contract.history.WorkoutHistoryRepository
import de.stefan.lang.shapebyte.features.workout.data.contract.quick.QuickWorkoutsDatasource
import de.stefan.lang.shapebyte.features.workout.data.contract.quick.QuickWorkoutsRepository
import de.stefan.lang.shapebyte.features.workout.data.contract.schedule.WorkoutScheduleDatasource
import de.stefan.lang.shapebyte.features.workout.data.contract.schedule.WorkoutScheduleEntry
import de.stefan.lang.shapebyte.features.workout.data.contract.schedule.WorkoutScheduleRepository
import de.stefan.lang.shapebyte.features.workout.data.fixture.QuickWorkoutsDatasourceFixture
import de.stefan.lang.shapebyte.features.workout.data.fixture.WorkoutHistoryDataSourceFixture
import de.stefan.lang.shapebyte.features.workout.data.fixture.WorkoutScheduleDatasourceFixture
import de.stefan.lang.shapebyte.features.workout.data.generated.GeneratedDependencies
import org.koin.core.component.get

object WorkoutDataModule :
    RootModule(
        globalBindings = {
            single<WorkoutHistoryRepository> { WorkoutHistoryRepository(dataSource = get()) }
            single<WorkoutScheduleRepository> { WorkoutScheduleRepository(datasource = get()) }

            // TODO: change once implemented !!!
            single<QuickWorkoutsDatasource> { QuickWorkoutsDatasourceFixture() }
            single<QuickWorkoutsRepository> {
                QuickWorkoutsRepository(
                    dataSource = get(),
                    logger = get(),
                )
            }

            // TODO: change once implemented !!!
            single<QuickWorkoutsDatasource> { QuickWorkoutsDatasourceFixture() }
            single<QuickWorkoutsRepository> {
                QuickWorkoutsRepository(
                    dataSource = get(),
                    logger = get(),
                )
            }

        },
        productionBindings = {
            single<WorkoutHistoryDataSource> { WorkoutHistoryDataSourceFixture } // TODO: change once implemented !!!
            single<WorkoutScheduleDatasource> { WorkoutScheduleDatasourceFixture } // TODO: change once implemented !!!
        },
        testBindings = {
            single<WorkoutHistoryDataSource> { WorkoutHistoryDataSourceFixture }
            single<WorkoutScheduleDatasource> { WorkoutScheduleDatasourceFixture }
        },
        dependencies = GeneratedDependencies.modules,
    ),
    WorkoutDataContract {
    override fun workoutHistoryEntry(scheduleEntry: WorkoutScheduleEntry): WorkoutHistoryEntry =
        WorkoutHistoryEntry(
            entry = scheduleEntry,
            dateStringFormatter = FoundationCoreModule.dateTimeStringFormatter(),
        )
}
