package de.stefan.lang.shapebyte.features.home

import de.stefan.lang.shapebyte.featureToggles.FeatureTogglesModule
import de.stefan.lang.shapebyte.features.home.di.HomeModule
import de.stefan.lang.shapebyte.features.workout.di.WorkoutModule
import de.stefan.lang.shapebyte.featuretest.FeatureTest

open class BaseHomeFeatureTest: FeatureTest() {
    override val testModules = listOf(
        FeatureTogglesModule.testModule,
        WorkoutModule.testModule,
        HomeModule.testModule
    )
}