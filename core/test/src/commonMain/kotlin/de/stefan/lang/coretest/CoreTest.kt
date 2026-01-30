package de.stefan.lang.coretest

import de.stefan.lang.coroutines.CoreCoroutinesModule
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.core.module.Module
import kotlin.test.AfterTest
import kotlin.test.BeforeTest

@OptIn(ExperimentalCoroutinesApi::class)
abstract class CoreTest {
    private val testDispatcher: CoroutineDispatcher by lazy {
        CoreCoroutinesModule
            .coroutineContextProvider()
            .mainDispatcher()
    }

    private val requiredModules: List<Module> = listOf(
        CoreCoroutinesModule.testModules,
    )

    protected open val autostartKoin: Boolean = true

    open val testModules: List<Module> = emptyList()

    @BeforeTest
    fun startDI() {
        try {
            startKoin { modules(requiredModules + testModules) }
        } catch (ex: Throwable) {
            print(ex)
        }
    }

    @AfterTest
    fun tearDown() {
        Dispatchers.resetMain()
        stopKoin()
    }

    fun test(block: suspend CoroutineScope.() -> Unit) {
        Dispatchers.setMain(testDispatcher)

        runTest {
            block()
        }
    }

    companion object
}
