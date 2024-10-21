package de.stefan.lang.shapebyte.di

import de.stefan.lang.shapebyte.features.home.di.HomeModule
import de.stefan.lang.shapebyte.features.workout.di.WorkoutModule
import de.stefan.lang.shapebyte.features.workout.item.ui.CountdownItemSetsViewModel
import de.stefan.lang.shapebyte.shared.featuretoggles.di.FeatureTogglesModule
import de.stefan.lang.shapebyte.utils.device.devicesize.DeviceSizeCategoryProviding
import de.stefan.lang.shapebyte.utils.device.devicesize.ScreenSizeProviding
import de.stefan.lang.shapebyte.utils.di.UtilsModule
import de.stefan.lang.shapebyte.utils.dimension.DimensionProvider
import de.stefan.lang.shapebyte.utils.logging.Logging
import org.koin.core.component.KoinComponent
import org.koin.core.component.get

object CommonMainModule : KoinComponent {
    val modules = UtilsModule.module +
        WorkoutModule.module +
        FeatureTogglesModule.module +
        HomeModule.module

    val testModules = UtilsModule.testModule +
        WorkoutModule.testModule +
        FeatureTogglesModule.testModule +
        HomeModule.testModule

    fun logger(): Logging = get()
    fun countdownItemSetsViewModel(): CountdownItemSetsViewModel = get()
    fun dimensionProvider(): DimensionProvider = get()
    fun screenSizesProvider(): ScreenSizeProviding = get()
    fun deviceSizeCategoryProvider(): DeviceSizeCategoryProviding = get()

    fun homeRootViewModel() = HomeModule.homeRootViewModel()
}
