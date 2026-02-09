package de.stefan.lang.shapebyte.features.home.presentation

import de.stefan.lang.shapebyte.features.home.presentation.contract.HomePresentationContract
import de.stefan.lang.shapebyte.features.home.presentation.contract.HomeRootViewModel
import de.stefan.lang.shapebyte.features.home.presentation.contract.HomeRootViewModelV2
import de.stefan.lang.shapebyte.features.home.presentation.generated.Dependencies
import de.stefan.lang.shapebyte.features.home.presentation.generated.Module
import de.stefan.lang.shapebyte.features.home.presentation.implementation.HomeRootViewModelImpl
import de.stefan.lang.shapebyte.features.home.presentation.implementation.HomeRootViewModelImplV2
import de.stefan.lang.shapebyte.features.navigation.contract.NavigationRequestHandler
import kotlinx.coroutines.coroutineScope
import org.koin.core.component.get
import org.koin.core.parameter.parametersOf

public object HomePresentationModule :
    Module(
        globalBindings = {
            factory<HomeRootViewModel> { (navHandler: NavigationRequestHandler) ->
                HomeRootViewModelImpl(
                    logger = Dependencies.logger(),
                    coroutineContextProvider = Dependencies.coroutineContextProvider(),
                    navigationHandler = navHandler,
                    currentWorkoutScheduleEntryUseCase = Dependencies.currentWorkoutScheduleEntryUseCase(),
                    recentHistoryUseCase = Dependencies.fetchRecentWorkoutHistoryUseCase(),
                    quickWorkoutsUseCase = Dependencies.quickWorkoutsUseCase(),
                    navigationRequestBuilder = Dependencies.navigationRequestBuilder(),
                    dateTimeStringFormatter = Dependencies.dateTimeStringFormatter(),
                )
            }

            factory<HomeRootViewModelV2> { (navHandler: NavigationRequestHandler) ->
                HomeRootViewModelImplV2(
                    logger = Dependencies.logger(),
                    coroutineContextProvider = Dependencies.coroutineContextProvider(),
                    coroutineScopeProvider = Dependencies.coroutineScopeProvider(),
                    navigationHandler = navHandler,
                    currentWorkoutScheduleEntryUseCase = Dependencies.currentWorkoutScheduleEntryUseCase(),
                    recentHistoryUseCase = Dependencies.fetchRecentWorkoutHistoryUseCase(),
                    quickWorkoutsUseCase = Dependencies.quickWorkoutsUseCase(),
                    navigationRequestBuilder = Dependencies.navigationRequestBuilder(),
                    dateTimeStringFormatter = Dependencies.dateTimeStringFormatter(),
                )
            }
        },
    ),
    HomePresentationContract {

    override fun homeRootViewModel(
        navigationHandler: NavigationRequestHandler,
    ): HomeRootViewModel = get(parameters = { parametersOf(navigationHandler) })

    override  fun homeRootViewModelV2(
        navigationHandler: NavigationRequestHandler,
    ): HomeRootViewModelV2 = get(parameters = { parametersOf(navigationHandler) })
}
