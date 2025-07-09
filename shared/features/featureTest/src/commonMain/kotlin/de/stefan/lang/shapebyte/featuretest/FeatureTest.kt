package de.stefan.lang.shapebyte.featuretest

import de.stefan.lang.core.CoreModule
import de.stefan.lang.coretest.CoreTest
import de.stefan.lang.foundation.FoundationModule
import org.koin.core.module.Module
import org.koin.test.KoinTest

abstract class FeatureTest : CoreTest(), KoinTest {
    override val testModules: List<Module> = listOf(
        CoreModule.testModule,
        FoundationModule.testModule,
    )
}
