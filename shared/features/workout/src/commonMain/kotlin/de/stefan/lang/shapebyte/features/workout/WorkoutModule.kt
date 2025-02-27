package de.stefan.lang.shapebyte.features.workout

import de.stefan.lang.coreutils.di.DIModuleDeclaration
import de.stefan.lang.coreutils.di.RootDIModule
import de.stefan.lang.navigation.NavigationHandling
import de.stefan.lang.shapebyte.features.workout.countdown.CountdownItemSetsViewModel
import de.stefan.lang.shapebyte.features.workout.workout.TimedWorkoutViewModel
import de.stefan.lang.shapebyte.features.workout.workout.WorkoutHistoryEntry
import de.stefan.lang.shapebyte.features.workout.workoutData.WorkoutDataModule
import de.stefan.lang.shapebyte.features.workout.workoutData.WorkoutDataModuleProviding
import de.stefan.lang.shapebyte.features.workout.workoutData.item.Item
import de.stefan.lang.shapebyte.features.workout.workoutData.item.ItemSet
import de.stefan.lang.shapebyte.features.workout.workoutData.mocks.WorkoutScheduleEntry
import de.stefan.lang.shapebyte.features.workout.workoutDomain.WorkoutDomainModule
import de.stefan.lang.shapebyte.features.workout.workoutDomain.WorkoutDomainModuleProviding
import de.stefan.lang.shapebyte.features.workout.workoutDomain.item.TimedItemExecution
import org.koin.core.component.get
import org.koin.core.parameter.parametersOf

interface WorkoutModuleProviding : WorkoutDataModuleProviding, WorkoutDomainModuleProviding {
    fun countdownItemSetsViewModel(): CountdownItemSetsViewModel
    fun timedWorkoutViewModel(navHandler: NavigationHandling): TimedWorkoutViewModel
    fun workoutHistoryEntry(scheduleEntry: WorkoutScheduleEntry): WorkoutHistoryEntry
}

object WorkoutModule :
    RootDIModule(
        providedModule = DIModuleDeclaration(
            allEnvironments = {
                factory<TimedWorkoutViewModel> { (navHandler: NavigationHandling) ->
                    TimedWorkoutViewModel(
                        navigationHandler = navHandler,
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
                        logger = get(),
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
    override fun timedWorkoutViewModel(navHandler: NavigationHandling): TimedWorkoutViewModel = get(
        parameters = {
            parametersOf(navHandler)
        },
    )
    override fun workoutHistoryEntry(scheduleEntry: WorkoutScheduleEntry): WorkoutHistoryEntry =
        get(
            parameters = {
                parametersOf(scheduleEntry)
            },
        )
}
