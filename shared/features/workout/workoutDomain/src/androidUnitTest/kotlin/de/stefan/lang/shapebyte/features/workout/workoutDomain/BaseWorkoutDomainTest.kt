package de.stefan.lang.shapebyte.features.workout.workoutDomain

import de.stefan.lang.shapebyte.featureToggles.FeatureTogglesModule
import de.stefan.lang.shapebyte.features.workout.workoutData.WorkoutDataModule
import de.stefan.lang.shapebyte.featuretest.FeatureTest
import org.koin.core.module.Module

open class BaseWorkoutDomainTest: FeatureTest() {
    override val testModules: List<Module> = super.testModules + listOf(
        FeatureTogglesModule.testModules,
        WorkoutDataModule.testModules,
        WorkoutDomainModule.testModules
    )
}