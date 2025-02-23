package de.stefan.lang.shapebyte.features.home

import de.stefan.lang.coreutils.di.DIModuleDeclaration
import de.stefan.lang.coreutils.di.RootDIModule
import org.koin.core.component.get

interface HomeModuleProviding {
    fun homeRootViewModel(): HomeRootViewModel
}

object HomeModule :
    RootDIModule(
        DIModuleDeclaration(
            allEnvironments = {
                single<HomeRootViewModel> {
                    HomeRootViewModel(
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
    override fun homeRootViewModel(): HomeRootViewModel = get()
}
