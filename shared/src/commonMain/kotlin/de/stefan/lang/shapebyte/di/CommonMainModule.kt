package de.stefan.lang.shapebyte.di

import de.stefan.lang.shapebyte.features.workout.di.WorkoutModule
import de.stefan.lang.shapebyte.features.workout.ui.CountdownItemSetsViewModel
import de.stefan.lang.shapebyte.shared.featuretoggles.di.FeatureTogglesModule
import de.stefan.lang.shapebyte.utils.Logging
import de.stefan.lang.shapebyte.utils.di.UtilsModule
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

object CommonMainModule : KoinComponent {
    val modules = UtilsModule.module +
        WorkoutModule.module +
        FeatureTogglesModule.module

    val testModules = UtilsModule.testModule +
        WorkoutModule.testModule +
        FeatureTogglesModule.testModule

    val logger: Logging by inject()
    val countdownItemSetsViewModel: CountdownItemSetsViewModel by inject()
}
