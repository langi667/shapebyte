package de.stefan.lang.shapebyte.featureTogglesData

import de.stefan.lang.core.CoreModule
import de.stefan.lang.shapebyte.featuretest.FeatureTest
import org.koin.core.module.Module

open class BaseFeatureToggleDataTest : FeatureTest() {
    override val testModules: List<Module>
        get() = super.testModules + listOf(
            CoreModule.testModule,
            FeatureTogglesDataModule.testModule,
        )
}
