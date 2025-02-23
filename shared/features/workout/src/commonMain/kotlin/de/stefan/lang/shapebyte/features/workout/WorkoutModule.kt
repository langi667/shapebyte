package de.stefan.lang.shapebyte.features.workout

import de.stefan.lang.coreutils.di.DIModuleDeclaration
import de.stefan.lang.coreutils.di.RootDIModule
import de.stefan.lang.shapebyte.features.workout.workoutData.WorkoutScheduleEntry
import de.stefan.lang.shapebyte.features.workout.workoutData.Item
import de.stefan.lang.shapebyte.features.workout.workoutData.ItemSet
import de.stefan.lang.shapebyte.features.workout.workoutData.WorkoutDataModule
import de.stefan.lang.shapebyte.features.workout.workoutData.WorkoutDataModuleProviding
import de.stefan.lang.shapebyte.features.workout.workoutDomain.TimedItemExecution
import de.stefan.lang.shapebyte.features.workout.workoutDomain.WorkoutDomainModule
import de.stefan.lang.shapebyte.features.workout.workoutDomain.WorkoutDomainModuleProviding
import org.koin.core.component.get
import org.koin.core.parameter.parametersOf

interface WorkoutModuleProviding: WorkoutDataModuleProviding, WorkoutDomainModuleProviding {
    fun countdownItemSetsViewModel(): CountdownItemSetsViewModel
    fun timedWorkoutViewModel(): TimedWorkoutViewModel
    fun workoutHistoryEntry(scheduleEntry: WorkoutScheduleEntry): WorkoutHistoryEntry
}

object WorkoutModule : RootDIModule(
    providedModule = DIModuleDeclaration(
        allEnvironments = {
            single<TimedWorkoutViewModel> {
                TimedWorkoutViewModel(
                    quickWorkoutForIdUseCase = get(),
                    itemsExecutionBuilder = get(),
                    dateStringFormatter = get(),
                    logger = get(),
                    audioPlayer = get(),
                    coroutineContextProvider = get(),
                )
            }

            factory {
                CountdownItemSetsViewModel(
                    logger = get(),
                    coroutineContextProvider = get(),
                )
            }
            factory<WorkoutHistoryEntry> { (entry: WorkoutScheduleEntry) ->
                WorkoutHistoryEntry(
                    entry = entry, dateStringFormatter = get(),
                )
            }
            factory<TimedItemExecution> { (item: Item, sets: List<ItemSet.Timed.Seconds>) ->
                TimedItemExecution(
                    item = item,
                    sets = sets,
                    logger = get()
                )
            }
        },
        appEnvironmentOnly = {
        },
        testEnvironmentOnly = {
        },
    ),

    diModules = listOf(
        WorkoutDataModule,
        WorkoutDomainModule,
    ),
),
    WorkoutDataModuleProviding by WorkoutDataModule,
    WorkoutDomainModuleProviding by WorkoutDomainModule,
    WorkoutModuleProviding {
        override fun countdownItemSetsViewModel(): CountdownItemSetsViewModel = get()
        override fun timedWorkoutViewModel(): TimedWorkoutViewModel = get()
    override fun workoutHistoryEntry(scheduleEntry: WorkoutScheduleEntry): WorkoutHistoryEntry =
        get(
            parameters = {
                parametersOf(scheduleEntry)
            },
        )
    }
