package de.stefan.lang.shapebyte.featureTogglesDomain

import de.stefan.lang.shapebyte.featureTogglesData.FeatureTogglesDataModule
import de.stefan.lang.shapebyte.featuretest.FeatureTest
import org.koin.core.module.Module

open class BaseFeatureToggleDomainTest : FeatureTest() {
    override val testModules: List<Module>
        get() = listOf(
            FeatureTogglesDataModule.testModule,
            FeatureTogglesDomainModule.testModule,
        )
}
