package de.stefan.lang.shapebyte.features.home.presentation

import de.stefan.lang.core.di.RootModule
import de.stefan.lang.shapebyte.features.home.presentation.contract.HomePresentationContract
import de.stefan.lang.shapebyte.features.home.presentation.contract.HomeRootViewModel
import de.stefan.lang.shapebyte.features.home.presentation.generated.GeneratedDependencies
import de.stefan.lang.shapebyte.features.home.presentation.implementation.HomeRootViewModelImpl
import de.stefan.lang.shapebyte.features.navigation.contract.NavigationRequestHandling
import org.koin.core.component.get
import org.koin.core.parameter.parametersOf

public object HomePresentationModule :
    RootModule(
        allEnvironments = {
            factory<HomeRootViewModel> { (navHandler: NavigationRequestHandling) ->
                HomeRootViewModelImpl(
                    logger = get(),
                    coroutineContextProvider = get(),
                    navigationHandler = navHandler,
                    currentWorkoutScheduleEntryUseCase = get(),
                    recentHistoryUseCase = get(),
                    quickWorkoutsUseCase = get(),
                    navigationRequestBuilder = get(),
                    dateTimeStringFormatter = get(),
                )
            }
        },
        dependencies = GeneratedDependencies.modules,
    ),
    HomePresentationContract {
    override fun homeRootViewModel(
        navigationHandler: NavigationRequestHandling,
    ): HomeRootViewModel = get(parameters = { parametersOf(navigationHandler) })
}
