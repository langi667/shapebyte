package de.stefan.lang.shapebyte.featureTogglesDomain

import de.stefan.lang.coretest.CoreTest
import de.stefan.lang.utils.logging.LoggingModule
import org.koin.core.module.Module
import org.koin.test.KoinTest

open class BaseFeatureToggleDomainTest : CoreTest(), KoinTest {
    override val testModules: List<Module>
        get() = super.testModules + listOf(
            LoggingModule.testDiModule,
        )
}
