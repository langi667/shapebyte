package de.stefan.lang.coretest

import de.stefan.lang.coroutines.CoroutinesModule
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
public abstract class CoreTest {
    private val testDispatcher: CoroutineDispatcher by lazy {
        CoroutinesModule
            .coroutineContextProvider()
            .mainDispatcher()
    }

    private val requiredModules: List<Module> = listOf(
        CoroutinesModule.testDiModule,
    )

    protected open val autostartKoin: Boolean = true

    protected open val testModules: List<Module> = emptyList()

    @BeforeTest
    public fun startDI() {
        stopKoin()
        if (!autostartKoin) return
        try {
            startKoin { modules(requiredModules + testModules) }
        } catch (ex: Throwable) {
            print(ex)
        }
    }

    @AfterTest
    public fun tearDown() {
        Dispatchers.resetMain()
        stopKoin()
    }

    protected fun test(block: suspend CoroutineScope.() -> Unit) {
        Dispatchers.setMain(testDispatcher)
        runTest {
            block()
        }
    }

    public companion object
}
