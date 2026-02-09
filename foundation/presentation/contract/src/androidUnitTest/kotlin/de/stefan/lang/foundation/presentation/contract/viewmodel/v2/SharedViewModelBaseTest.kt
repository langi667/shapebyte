package de.stefan.lang.foundation.presentation.contract.viewmodel.v2

import app.cash.turbine.test
import de.stefan.lang.coretest.CoreTest
import de.stefan.lang.coroutines.CoroutinesModule
import de.stefan.lang.foundation.presentation.contract.state.UIState
import de.stefan.lang.utils.logging.LoggingModule
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlin.test.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class SharedViewModelBaseTest: CoreTest() {

    override val testModules = super.testModules + listOf(
        LoggingModule.testDiModule,
        CoroutinesModule.testDiModule
    )

    @Test
    fun updateState_emitsNewValue() = test {
        val sut = createSUT()

        sut.state.test {
            assertEquals(UIState.Idle, awaitItem())

            val newState = UIState.Loading
            sut.updateState(newState)

            assertEquals(newState, awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun emitEvent_emitsEventThroughEventFlow() = test {
        val sut = createSUT()

        sut.eventFlow.test {
            val event = TestEvent
            sut.emitEvent(event)

            assertEquals(event, awaitItem())
            cancelAndIgnoreRemainingEvents()
        }

    }

    @Test
    fun clear_invokesOnCleared() = test {
        val sut = createSUT()
        val callback = mockk<() -> Unit>(relaxed = true)

        sut.onClearedCallback = callback

        sut.clear()

        verify(exactly = 1) { callback.invoke() }
    }

    private fun createSUT() = TestCoreViewModel(
        logger = LoggingModule.logger(),
        coroutineContextProvider = CoroutinesModule.coroutineContextProvider(),
        coroutineScopeProvider = CoroutinesModule.coroutineScopeProvider(),
    )


}
