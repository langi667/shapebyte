package de.stefan.lang.shapebyte.featureTogglesDomain

import de.stefan.lang.coretest.CoreTest
import de.stefan.lang.coreutils.CoreUtilsModule
import de.stefan.lang.coroutines.CoroutinesModule
import de.stefan.lang.foundationCore.FoundationCoreModule
import de.stefan.lang.foundationUI.FoundationUIModule
import de.stefan.lang.shapebyte.featureTogglesData.FeatureTogglesDataModule
import org.koin.core.module.Module
import org.koin.test.KoinTest

open class BaseFeatureToggleDomainTest : CoreTest(), KoinTest {
    override val testModules: List<Module>
        get() = super.testModules + listOf(
            CoreUtilsModule.testModules,
            CoroutinesModule.testModules,
            FoundationCoreModule.testModules,
            FoundationUIModule.testModules,
            FeatureTogglesDataModule.testModules,
            FeatureTogglesDomainModule.testModules,
        )
}
