package de.stefan.lang.shapebyte.features.workout

import de.stefan.lang.core.CoreModule
import de.stefan.lang.coretest.CoreTest
import de.stefan.lang.foundationCore.FoundationCoreModule
import de.stefan.lang.foundationUI.FoundationUIModule
import de.stefan.lang.shapebyte.featureToggles.FeatureTogglesModule
import org.koin.core.module.Module
import org.koin.test.KoinTest

open class WorkoutFeatureTest: CoreTest(), KoinTest {
    override val testModules: List<Module> = super.testModules + listOf(
        CoreModule.testModules,
        FoundationCoreModule.testModules,
        FoundationUIModule.testModules,
        FeatureTogglesModule.testModules,
        WorkoutModule.testModules
    )
}
