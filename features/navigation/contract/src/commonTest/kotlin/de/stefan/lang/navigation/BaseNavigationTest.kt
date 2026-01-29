package de.stefan.lang.navigation

import de.stefan.lang.coretest.CoreTest
import de.stefan.lang.shapebyte.features.navigation.NavigationModule
import org.koin.core.module.Module
import org.koin.test.KoinTest

open class BaseNavigationTest : CoreTest(), KoinTest {
    override val testModules: List<Module> =
        listOf(NavigationModule.testModules)
}
