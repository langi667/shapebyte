package de.stefan.lang.navigation

import de.stefan.lang.shapebyte.featuretest.FeatureTest
import org.koin.core.module.Module

open class BaseNavigationTest : FeatureTest() {
    override val testModules: List<Module> = listOf(NavigationModule.testModules)
}
