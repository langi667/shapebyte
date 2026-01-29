package de.stefan.lang.shapebyte.features.workout

import de.stefan.lang.coretest.CoreTest
import org.koin.core.module.Module
import org.koin.test.KoinTest

open class WorkoutFeatureTest: CoreTest(), KoinTest {
    override val testModules: List<Module> = super.testModules + listOf(
        WorkoutModule.testModules
    )
}
