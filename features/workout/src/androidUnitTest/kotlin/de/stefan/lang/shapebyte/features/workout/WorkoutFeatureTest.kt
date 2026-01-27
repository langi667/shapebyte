package de.stefan.lang.shapebyte.features.workout

import de.stefan.lang.coretest.CoreTest
import de.stefan.lang.coreutils.CoreUtilsModule
import de.stefan.lang.coroutines.CoroutinesModule
import de.stefan.lang.foundationCore.FoundationCoreModule
import de.stefan.lang.foundation.presentation.FoundationPresentationModule
import de.stefan.lang.shapebyte.featureToggles.FeatureTogglesModule
import de.stefan.lang.utils.logging.LoggingModule
import org.koin.core.module.Module
import org.koin.test.KoinTest

open class WorkoutFeatureTest: CoreTest(), KoinTest {
    override val testModules: List<Module> = super.testModules + listOf(
        WorkoutModule.testModules
    )
}
