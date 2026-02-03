package de.stefan.lang.shapebyte.features.workout.data

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
import de.stefan.lang.shapebyte.features.workout.data.generated.Dependencies
import de.stefan.lang.shapebyte.features.workout.data.generated.Module
import org.koin.core.component.get

object WorkoutDataModule :
    Module(
        globalBindings = {
            single<WorkoutHistoryRepository> { WorkoutHistoryRepository(dataSource = get()) }
            single<WorkoutScheduleRepository> { WorkoutScheduleRepository(datasource = get()) }

            // TODO: change once implemented !!!
            single<QuickWorkoutsDatasource> { QuickWorkoutsDatasourceFixture() }
            single<QuickWorkoutsRepository> {
                QuickWorkoutsRepository(
                    dataSource = get(),
                    logger = Dependencies.logger(),
                )
            }

            // TODO: change once implemented !!!
            single<QuickWorkoutsDatasource> { QuickWorkoutsDatasourceFixture() }
            single<QuickWorkoutsRepository> {
                QuickWorkoutsRepository(
                    dataSource = get(),
                    logger = Dependencies.logger(),
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
    ),
    WorkoutDataContract {
    override fun workoutHistoryEntry(scheduleEntry: WorkoutScheduleEntry): WorkoutHistoryEntry =
        WorkoutHistoryEntry(
            entry = scheduleEntry,
            dateStringFormatter = Dependencies.dateTimeStringFormatter(),
        )

    override fun workoutHistoryRepository(): WorkoutHistoryRepository = get()

    override fun workoutScheduleRepository(): WorkoutScheduleRepository = get()

    override fun quickWorkoutsRepository(): QuickWorkoutsRepository = get()
}
