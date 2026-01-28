package de.stefan.lang.shapebyte.featureToggles

import de.stefan.lang.core.di.RootModule
import de.stefan.lang.shapebyte.featureTogglesDomain.FeatureTogglesDomainModule

interface FeatureTogglesModuleProviding

object FeatureTogglesModule :
    RootModule(
        listOf(
            FeatureTogglesDomainModule,
        ),
    ),
    FeatureTogglesModuleProviding
