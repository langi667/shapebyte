package de.stefan.lang.navigation

import de.stefan.lang.coretest.CoreTest
import org.koin.core.module.Module
import org.koin.test.KoinTest

open class BaseNavigationTest : CoreTest(), KoinTest {
    override val testModules: List<Module> =
        listOf()
}
