package de.stefan.lang.shapebyte.featureTogglesData

import de.stefan.lang.core.CoreModule
import de.stefan.lang.coretest.CoreTest
import org.koin.core.module.Module
import org.koin.test.KoinTest

open class BaseFeatureToggleDataTest : CoreTest(), KoinTest {
    override val testModules: List<Module>
        get() = super.testModules + listOf(
            CoreModule.testModules,
            FeatureTogglesDataModule.testModules,
        )
}
