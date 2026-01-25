package de.stefan.lang.shapebyte.featureTogglesDomain

import de.stefan.lang.core.CoreModule
import de.stefan.lang.coretest.CoreTest
import de.stefan.lang.foundationCore.FoundationCoreModule
import de.stefan.lang.foundationUI.FoundationUIModule
import de.stefan.lang.shapebyte.featureTogglesData.FeatureTogglesDataModule
import org.koin.core.module.Module
import org.koin.test.KoinTest

open class BaseFeatureToggleDomainTest : CoreTest(), KoinTest {
    override val testModules: List<Module>
        get() = super.testModules + listOf(
            CoreModule.testModules,
            FoundationCoreModule.testModules,
            FoundationUIModule.testModules,
            FeatureTogglesDataModule.testModules,
            FeatureTogglesDomainModule.testModules,
        )
}
