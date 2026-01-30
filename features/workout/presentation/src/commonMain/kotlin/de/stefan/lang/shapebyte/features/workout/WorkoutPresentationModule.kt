package de.stefan.lang.shapebyte.features.workout

import de.stefan.lang.core.di.RootModule
import de.stefan.lang.shapebyte.features.navigation.contract.NavigationRequestHandling
import de.stefan.lang.shapebyte.features.workout.contract.WorkoutPresentationContract
import de.stefan.lang.shapebyte.features.workout.contract.countdown.CountdownItemSetsViewModel
import de.stefan.lang.shapebyte.features.workout.contract.timed.TimedWorkoutViewModel
import de.stefan.lang.shapebyte.features.workout.domain.WorkoutDomainModule.createTimedItemExecution
import de.stefan.lang.shapebyte.features.workout.impl.countdown.CountdownItemSetsViewModelImpl
import de.stefan.lang.shapebyte.features.workout.impl.timed.TimedWorkoutViewModelImpl
import org.koin.core.component.get
import org.koin.core.parameter.parametersOf

object WorkoutPresentationModule :
    RootModule(
        globalBindings = {
            factory<TimedWorkoutViewModel> { (navHandler: NavigationRequestHandling) ->
                TimedWorkoutViewModelImpl(
                    navigationHandler = navHandler,
                    quickWorkoutForIdUseCase = get(),
                    itemsExecutionBuilder = get(),
                    dateStringFormatter = get(),
                    logger = get(),
                    audioPlayer = get(),
                    coroutineContextProvider = get(),
                )
            }

            factory<CountdownItemSetsViewModel> {
                CountdownItemSetsViewModelImpl(
                    logger = get(),
                    coroutineContextProvider = get(),
                    timedHandlerFactory = {
                            item, sets ->
                        createTimedItemExecution(item, sets)
                    },
                )
            }
        },
    ),
    WorkoutPresentationContract {
    override fun countdownItemSetsViewModel(): CountdownItemSetsViewModel = get()
    override fun timedWorkoutViewModel(navHandler: NavigationRequestHandling): TimedWorkoutViewModel {
        return get { parametersOf(navHandler) }
    }
}
