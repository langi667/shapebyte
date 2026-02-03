package de.stefan.lang.shapebyte.features.home.presentation

import de.stefan.lang.shapebyte.features.home.presentation.contract.HomePresentationContract
import de.stefan.lang.shapebyte.features.home.presentation.contract.HomeRootViewModel
import de.stefan.lang.shapebyte.features.home.presentation.generated.Dependencies
import de.stefan.lang.shapebyte.features.home.presentation.generated.Module
import de.stefan.lang.shapebyte.features.home.presentation.implementation.HomeRootViewModelImpl
import de.stefan.lang.shapebyte.features.navigation.contract.NavigationRequestHandling
import org.koin.core.component.get
import org.koin.core.parameter.parametersOf

public object HomePresentationModule :
    Module(
        globalBindings = {
            factory<HomeRootViewModel> { (navHandler: NavigationRequestHandling) ->
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
        },
    ),
    HomePresentationContract {
    override fun homeRootViewModel(
        navigationHandler: NavigationRequestHandling,
    ): HomeRootViewModel = get(parameters = { parametersOf(navigationHandler) })
}
