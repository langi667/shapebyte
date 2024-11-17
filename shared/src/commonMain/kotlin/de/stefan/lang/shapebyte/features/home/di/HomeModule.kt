package de.stefan.lang.shapebyte.features.home.di

import de.stefan.lang.shapebyte.features.home.ui.HomeRootViewModel
import de.stefan.lang.shapebyte.utils.dicore.DIModuleDeclaration
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
                    fetchQuickWorkoutsUseCase = get(),
                    logger = get(),
                )
            }
        },
    ),
    HomeModuleProviding {

    override fun homeRootViewModel(): HomeRootViewModel = get()
}
