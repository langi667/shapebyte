package de.stefan.lang.features

import de.stefan.lang.core.di.RootDIModule
import de.stefan.lang.shapebyte.featureToggles.FeatureTogglesModule
import de.stefan.lang.shapebyte.featureToggles.FeatureTogglesModuleProviding
import de.stefan.lang.shapebyte.features.home.HomeModule
import de.stefan.lang.shapebyte.features.home.HomeModuleProviding
import de.stefan.lang.shapebyte.features.workout.WorkoutModule
import de.stefan.lang.shapebyte.features.workout.WorkoutModuleProviding

interface FeaturesModuleProviding :
    FeatureTogglesModuleProviding,
    HomeModuleProviding,
    WorkoutModuleProviding

object FeaturesModule :
    RootDIModule(
        listOf(
            FeatureTogglesModule,
            HomeModule,
            WorkoutModule,
        ),
    ),
    FeaturesModuleProviding,
    FeatureTogglesModuleProviding by FeatureTogglesModule,
    HomeModuleProviding by HomeModule,
    WorkoutModuleProviding by WorkoutModule
