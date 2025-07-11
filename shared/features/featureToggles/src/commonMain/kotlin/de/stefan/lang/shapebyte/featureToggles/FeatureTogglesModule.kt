package de.stefan.lang.shapebyte.featureToggles

import de.stefan.lang.core.di.RootDIModule
import de.stefan.lang.shapebyte.featureTogglesDomain.FeatureTogglesDomainModule

interface FeatureTogglesModuleProviding

object FeatureTogglesModule :
    RootDIModule(
        listOf(
            FeatureTogglesDomainModule,
        ),
    ),
    FeatureTogglesModuleProviding
