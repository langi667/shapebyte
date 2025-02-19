package de.stefan.lang.features

import de.stefan.lang.coreutils.di.RootDIModule
import de.stefan.lang.shapebyte.featureCore.FeatureCoreModule
import de.stefan.lang.shapebyte.featureCore.FeatureCoreModuleProviding
import de.stefan.lang.shapebyte.featureToggles.FeatureTogglesModule
import de.stefan.lang.shapebyte.featureToggles.FeatureTogglesModuleProviding

interface FeaturesModuleProviding :
    FeatureCoreModuleProviding,
    FeatureTogglesModuleProviding

object FeaturesModule :
    RootDIModule(
        listOf(
            FeatureCoreModule,
            FeatureTogglesModule,
        ),
    ),
    FeaturesModuleProviding,
    FeatureCoreModuleProviding by FeatureCoreModule,
    FeatureTogglesModuleProviding by FeatureTogglesModule
