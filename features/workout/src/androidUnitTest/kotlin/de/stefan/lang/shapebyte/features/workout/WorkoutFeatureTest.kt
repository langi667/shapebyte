package de.stefan.lang.shapebyte.features.workout

import de.stefan.lang.coretest.CoreTest
import de.stefan.lang.coreutils.CoreUtilsModule
import de.stefan.lang.coroutines.CoroutinesModule
import de.stefan.lang.foundationCore.FoundationCoreModule
import de.stefan.lang.foundationUI.FoundationUIModule
import de.stefan.lang.shapebyte.featureToggles.FeatureTogglesModule
import org.koin.core.module.Module
import org.koin.test.KoinTest

open class WorkoutFeatureTest: CoreTest(), KoinTest {
    override val testModules: List<Module> = super.testModules + listOf(
        CoreUtilsModule.testModules,
        CoroutinesModule.testModules,
        FoundationCoreModule.testModules,
        FoundationUIModule.testModules,
        FeatureTogglesModule.testModules,
        WorkoutModule.testModules
    )
}
