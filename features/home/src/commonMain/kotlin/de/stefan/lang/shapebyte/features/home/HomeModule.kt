package de.stefan.lang.shapebyte.features.home

import de.stefan.lang.core.di.ModuleBindings
import de.stefan.lang.core.di.RootModule
import de.stefan.lang.foundation.presentation.FoundationPresentationModule
import de.stefan.lang.foundationCore.FoundationCoreModule
import de.stefan.lang.shapebyte.featureTogglesDomain.FeatureTogglesDomainModule
import de.stefan.lang.shapebyte.features.home.presentation.contract.HomeRootViewModel
import de.stefan.lang.shapebyte.features.home.presentation.implementation.HomeRootViewModelImpl
import de.stefan.lang.shapebyte.features.navigation.NavigationModule
import de.stefan.lang.shapebyte.features.navigation.contract.NavigationRequestHandling
import de.stefan.lang.shapebyte.features.workout.WorkoutModule
import de.stefan.lang.utils.logging.LoggingModule
import org.koin.core.component.get
import org.koin.core.parameter.parametersOf

interface HomeModuleProviding {
    fun homeRootViewModel(navHandler: NavigationRequestHandling): HomeRootViewModel
}

object HomeModule :
    RootModule(
        bindings = ModuleBindings(
            allEnvironments = {
                single<HomeRootViewModel> { (navHandler: NavigationRequestHandling) ->
                    HomeRootViewModelImpl(
                        navigationHandler = navHandler,
                        currentWorkoutScheduleEntryUseCase = get(),
                        recentHistoryUseCase = get(),
                        quickWorkoutsUseCase = get(),
                        logger = get(),
                        coroutineContextProvider = get(),
                        navigationRequestBuilder = get(),
                        dateTimeStringFormatter = get(),
                    )
                }
            },
        ),
        dependencies = listOf(
            LoggingModule,
            WorkoutModule,
            FoundationCoreModule,
            FoundationPresentationModule,
            FeatureTogglesDomainModule,
            NavigationModule,
        ),
    ),
    HomeModuleProviding {
    override fun homeRootViewModel(navHandler: NavigationRequestHandling): HomeRootViewModel = get(
        parameters = {
            parametersOf(navHandler)
        },
    )
}
