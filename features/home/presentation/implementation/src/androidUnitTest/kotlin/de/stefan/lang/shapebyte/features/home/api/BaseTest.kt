package de.stefan.lang.shapebyte.features.home.api

import de.stefan.lang.coretest.CoreTest
import de.stefan.lang.shapebyte.featureTogglesDomain.FeatureTogglesDomainModule
import de.stefan.lang.shapebyte.features.navigation.NavigationModule
import de.stefan.lang.shapebyte.features.workout.workoutData.WorkoutDataModule
import de.stefan.lang.shapebyte.features.workout.workoutDomain.WorkoutDomainModule
import de.stefan.lang.utils.logging.LoggingModule
import org.koin.core.module.Module
import org.koin.test.KoinTest


// TODO: dependencies must come from generated dependency list
open class BaseTest: CoreTest(), KoinTest {
    override val testModules: List<Module>
        get() = super.testModules +
                LoggingModule.testModules +
                NavigationModule.testModules +
                FeatureTogglesDomainModule.testModules +
                WorkoutDataModule.testModules +
                WorkoutDomainModule.testModules




}