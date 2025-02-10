package de.stefan.lang.shapebyte.features.home.di

import de.stefan.lang.coreutils.di.DIModuleDeclaration
import de.stefan.lang.shapebyte.features.home.ui.HomeRootViewModel
import org.koin.core.component.get

interface HomeModuleProviding {
    fun homeRootViewModel(): HomeRootViewModel
}

object HomeModule :
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

    HomeModuleProviding {
    override fun homeRootViewModel(): HomeRootViewModel = get()
}
