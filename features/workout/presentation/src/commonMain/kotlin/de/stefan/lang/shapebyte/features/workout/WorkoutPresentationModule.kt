package de.stefan.lang.shapebyte.features.workout

import de.stefan.lang.shapebyte.features.navigation.contract.NavigationRequestHandler
import de.stefan.lang.shapebyte.features.workout.contract.WorkoutPresentationContract
import de.stefan.lang.shapebyte.features.workout.contract.timed.TimedWorkoutViewModel
import de.stefan.lang.shapebyte.features.workout.impl.timed.TimedWorkoutViewModelImpl
import de.stefan.lang.shapebyte.features.workout.presentation.generated.Dependencies
import de.stefan.lang.shapebyte.features.workout.presentation.generated.Module
import org.koin.core.component.get
import org.koin.core.parameter.parametersOf

public object WorkoutPresentationModule :
    Module(
        globalBindings = {
            factory<TimedWorkoutViewModel> { (navHandler: NavigationRequestHandler) ->
                TimedWorkoutViewModelImpl(
                    navigationHandler = navHandler,
                    quickWorkoutForIdUseCase = Dependencies.quickWorkoutForIdUseCase(),
                    itemsExecutionBuilder = Dependencies.itemsExecutionBuilder(),
                    dateStringFormatter = Dependencies.dateTimeStringFormatter(),
                    logger = Dependencies.logger(),
                    audioPlayer = Dependencies.audioPlayer(),
                    coroutineContextProvider = Dependencies.coroutineContextProvider(),
                    coroutineScopeProvider = Dependencies.coroutineScopeProvider(),
                )
            }
        },
    ),
    WorkoutPresentationContract {
    public override fun timedWorkoutViewModel(navigationHandler: NavigationRequestHandler): TimedWorkoutViewModel {
        return get { parametersOf(navigationHandler) }
    }
}
