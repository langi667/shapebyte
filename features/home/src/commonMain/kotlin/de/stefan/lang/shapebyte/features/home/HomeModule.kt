package de.stefan.lang.shapebyte.features.home

import de.stefan.lang.core.di.DIModuleDeclaration
import de.stefan.lang.core.di.RootDIModule
import de.stefan.lang.foundation.presentation.FoundationPresentationModule
import de.stefan.lang.foundationCore.FoundationCoreModule
import de.stefan.lang.shapebyte.featureToggles.FeatureTogglesModule
import de.stefan.lang.shapebyte.features.home.presentation.contract.HomeRootViewModel
import de.stefan.lang.shapebyte.features.home.presentation.implementation.HomeRootViewModelImpl
import de.stefan.lang.shapebyte.features.navigation.NavigationModule
import de.stefan.lang.shapebyte.features.navigation.api.NavigationRequestHandling
import de.stefan.lang.shapebyte.features.workout.WorkoutModule
import org.koin.core.component.get
import org.koin.core.parameter.parametersOf

interface HomeModuleProviding {
    fun homeRootViewModel(navHandler: NavigationRequestHandling): HomeRootViewModel
}

object HomeModule :
    RootDIModule(
        DIModuleDeclaration(
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
        diModules = listOf(
            FeatureTogglesModule,
            WorkoutModule,
            FoundationCoreModule,
            FoundationPresentationModule,
            FeatureTogglesModule,
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
