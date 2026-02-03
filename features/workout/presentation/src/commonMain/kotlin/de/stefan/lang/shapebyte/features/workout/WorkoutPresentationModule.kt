package de.stefan.lang.shapebyte.features.workout

import de.stefan.lang.core.di.RootModule
import de.stefan.lang.shapebyte.features.navigation.contract.NavigationRequestHandling
import de.stefan.lang.shapebyte.features.workout.contract.WorkoutPresentationContract
import de.stefan.lang.shapebyte.features.workout.contract.countdown.CountdownItemSetsViewModel
import de.stefan.lang.shapebyte.features.workout.contract.timed.TimedWorkoutViewModel
import de.stefan.lang.shapebyte.features.workout.domain.WorkoutDomainModule
import de.stefan.lang.shapebyte.features.workout.impl.countdown.CountdownItemSetsViewModelImpl
import de.stefan.lang.shapebyte.features.workout.impl.timed.TimedWorkoutViewModelImpl
import de.stefan.lang.shapebyte.features.workout.presentation.generated.Dependencies
import org.koin.core.component.get
import org.koin.core.parameter.parametersOf

object WorkoutPresentationModule :
    RootModule(
        globalBindings = {
            factory<TimedWorkoutViewModel> { (navHandler: NavigationRequestHandling) ->
                TimedWorkoutViewModelImpl(
                    navigationHandler = navHandler,
                    quickWorkoutForIdUseCase = Dependencies.quickWorkoutForIdUseCase(),
                    itemsExecutionBuilder = Dependencies.itemsExecutionBuilder(),
                    dateStringFormatter = Dependencies.dateTimeStringFormatter(),
                    logger = Dependencies.logger(),
                    audioPlayer = Dependencies.audioPlayer(),
                    coroutineContextProvider = Dependencies.coroutineContextProvider(),
                )
            }

            factory<CountdownItemSetsViewModel> {
                CountdownItemSetsViewModelImpl(
                    logger = Dependencies.logger(),
                    coroutineContextProvider = Dependencies.coroutineContextProvider(),
                    timedHandlerFactory = {
                            item, sets ->
                        WorkoutDomainModule.createTimedItemExecution(item, sets)
                    },
                )
            }
        },

        dependencies = Dependencies.modules,
    ),
    WorkoutPresentationContract {
    override fun countdownItemSetsViewModel(): CountdownItemSetsViewModel = get()
    override fun timedWorkoutViewModel(navHandler: NavigationRequestHandling): TimedWorkoutViewModel {
        return get { parametersOf(navHandler) }
    }
}
