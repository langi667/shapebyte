package de.stefan.lang.shapebyte.features.workout

import de.stefan.lang.core.CoreModule
import de.stefan.lang.coretest.CoreTest
import de.stefan.lang.foundation.FoundationModule
import de.stefan.lang.shapebyte.featureToggles.FeatureTogglesModule
import org.koin.core.module.Module
import org.koin.test.KoinTest

open class WorkoutFeatureTest: CoreTest(), KoinTest {
    override val testModules: List<Module> = super.testModules + listOf(
        CoreModule.testModules,
        FoundationModule.testModules,
        FeatureTogglesModule.testModules,
        WorkoutModule.testModules
    )
}