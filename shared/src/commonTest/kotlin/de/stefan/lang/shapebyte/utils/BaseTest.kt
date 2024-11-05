package de.stefan.lang.shapebyte.utils

import de.stefan.lang.shapebyte.di.DPI
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import kotlin.test.AfterTest
import kotlin.test.BeforeTest

// TODO: separate file
abstract class BaseTest : KoinTest {
    // TODO: Improve this, WorkoutModule.testModule should not be in BaseTest
    private val testModules = DPI.testModules

    @BeforeTest
    fun startDI() {
        startKoin {
            modules(testModules)
        }
    }

    @AfterTest
    fun stopDI() {
        stopKoin()
    }
}
