package de.stefan.lang.shapebyte.features.workout

import de.stefan.lang.coretest.CoreTest
import de.stefan.lang.foundationCore.FoundationCoreModule
import de.stefan.lang.utils.logging.LoggingModule
import org.koin.core.module.Module
import org.koin.test.KoinTest

open class BaseTest : CoreTest(), KoinTest {
    override val testModules: List<Module> = super.testModules + listOf(
        FoundationCoreModule.testDiModule,
        LoggingModule.testDiModule,
    )
}