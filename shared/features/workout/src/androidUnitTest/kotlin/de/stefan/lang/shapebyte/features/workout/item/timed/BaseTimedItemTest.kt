package de.stefan.lang.shapebyte.features.workout.item.timed

import de.stefan.lang.shapebyte.featureToggles.FeatureTogglesModule
import de.stefan.lang.shapebyte.features.workout.WorkoutModule
import de.stefan.lang.shapebyte.featuretest.FeatureTest
import org.koin.core.module.Module

open class BaseTimedItemTest: FeatureTest() {
    override val testModules: List<Module>
        get() = listOf(
            FeatureTogglesModule.testModule,
            WorkoutModule.testModule
        )
}