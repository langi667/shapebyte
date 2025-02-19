package de.stefan.lang.shapebyte.featureToggles

import de.stefan.lang.shapebyte.featuretest.FeatureTest
import org.koin.core.module.Module

open class BaseFeatureToggleTest : FeatureTest() {
    override val testModules: List<Module>
        get() = listOf(FeatureTogglesModule.testModule)
}
