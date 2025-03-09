package de.stefan.lang.shapebyte.features.home

import de.stefan.lang.coreutils.di.DIModuleDeclaration
import de.stefan.lang.coreutils.di.RootDIModule
import de.stefan.lang.navigation.NavigationRequestHandling
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
                    HomeRootViewModel(
                        navigationHandler = navHandler,
                        currentWorkoutScheduleEntryUseCase = get(),
                        recentHistoryUseCase = get(),
                        quickWorkoutsUseCase = get(),
                        logger = get(),
                        coroutineContextProvider = get(),
                    )
                }
            },
        ),
        emptyList(),
    ),
    HomeModuleProviding {
    override fun homeRootViewModel(navHandler: NavigationRequestHandling): HomeRootViewModel = get(
        parameters = {
            parametersOf(navHandler)
        },
    )
}
