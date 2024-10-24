package de.stefan.lang.shapebyte.features.home.di

import de.stefan.lang.shapebyte.features.home.ui.HomeRootViewModel
import de.stefan.lang.shapebyte.utils.dicore.DIModule
import org.koin.core.component.get
import org.koin.dsl.module

interface HomeModuleProviding {
    fun homeRootViewModel(): HomeRootViewModel
}

object HomeModule : DIModule, HomeModuleProviding {
    override val module = module {
        single<HomeRootViewModel> {
            HomeRootViewModel(
                currentWorkoutScheduleEntryUseCase = get(),
                recentHistoryUseCase = get(),
                logger = get(),
            )
        }
    }

    override val testModule = module {
        single<HomeRootViewModel> {
            HomeRootViewModel(
                currentWorkoutScheduleEntryUseCase = get(),
                recentHistoryUseCase = get(),
                logger = get(),
            )
        }
    }

    override fun homeRootViewModel(): HomeRootViewModel = get()
}
