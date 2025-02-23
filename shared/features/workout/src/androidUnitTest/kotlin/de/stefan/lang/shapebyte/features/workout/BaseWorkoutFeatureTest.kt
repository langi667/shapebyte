package de.stefan.lang.shapebyte.features.workout

import de.stefan.lang.shapebyte.featureToggles.FeatureTogglesModule
import de.stefan.lang.shapebyte.featuretest.FeatureTest
import org.koin.core.module.Module

open class BaseWorkoutFeatureTest: FeatureTest() {
    override val testModules: List<Module> = listOf(
        FeatureTogglesModule.testModule,
        WorkoutModule.testModule
    )
}