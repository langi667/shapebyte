package de.stefan.lang.shapebyte.featureToggles

import de.stefan.lang.coreutils.di.RootDIModule
import de.stefan.lang.shapebyte.featureTogglesData.FeatureTogglesDataModule
import de.stefan.lang.shapebyte.featureTogglesData.FeatureTogglesDataModuleProviding
import de.stefan.lang.shapebyte.featureTogglesDomain.FeatureTogglesDomainModule
import de.stefan.lang.shapebyte.featureTogglesDomain.FeatureTogglesDomainModuleProviding

interface FeatureTogglesModuleProviding :
    FeatureTogglesDataModuleProviding,
    FeatureTogglesDomainModuleProviding

object FeatureTogglesModule :
    RootDIModule(
        listOf(
            FeatureTogglesDomainModule,
            FeatureTogglesDataModule,
        ),
    ),
    FeatureTogglesModuleProviding,
    FeatureTogglesDataModuleProviding by FeatureTogglesDataModule,
    FeatureTogglesDomainModuleProviding by FeatureTogglesDomainModule
