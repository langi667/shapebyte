package de.stefan.lang.shapebyte.featureTogglesData

import de.stefan.lang.coretest.CoreTest
import de.stefan.lang.coreutils.CoreUtilsModule
import de.stefan.lang.coroutines.CoroutinesModule
import org.koin.core.module.Module
import org.koin.test.KoinTest

open class BaseFeatureToggleDataTest : CoreTest(), KoinTest {
    override val testModules: List<Module>
        get() = super.testModules + listOf(
            CoreUtilsModule.testModules,
            CoroutinesModule.testModules,
            FeatureTogglesDataModule.testModules,
        )
}
