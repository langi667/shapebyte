package de.stefan.lang.foundation.presentation.contract.viewmodel.v2

import app.cash.turbine.test
import de.stefan.lang.coretest.CoreTest
import de.stefan.lang.coroutines.CoroutinesModule
import de.stefan.lang.foundation.presentation.contract.event.UIEvent
import de.stefan.lang.foundation.presentation.contract.state.UIState
import de.stefan.lang.utils.logging.LoggingModule
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlin.test.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class SharedViewModelWrapperTest : CoreTest() {

    override val testModules = super.testModules + listOf(
        LoggingModule.testDiModule,
        CoroutinesModule.testDiModule,
    )

    @Test
    fun updateState_emitsNewValueThroughWrapper() = test {
        val (wrapper, _) = createWrapper()

        wrapper.state.test {
            assertEquals(UIState.Idle, awaitItem())

            val newState = UIState.Loading
            wrapper.updateState(newState)

            assertEquals(newState, awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun emitEvent_emitsEventThroughWrapper() = test {
        val (wrapper, _) = createWrapper()

        wrapper.eventFlow.test {
            val event = WrapperTestEvent
            wrapper.emitEvent(event)

            assertEquals(event, awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun onCleared_clearsWrappedViewModel() = test {
        val (wrapper, wrapped) = createWrapper()
        val callback = mockk<() -> Unit>(relaxed = true)
        wrapped.onClearedCallback = callback

        wrapper.triggerOnCleared()

        verify(exactly = 1) { callback.invoke() }
    }

    private fun createWrapper(): WrapperSetup {
        val wrapped = TestCoreViewModel(
            logger = LoggingModule.logger(),
            coroutineContextProvider = CoroutinesModule.coroutineContextProvider(),
            coroutineScopeProvider = CoroutinesModule.coroutineScopeProvider(),
        )
        val wrapper = TestSharedViewModelWrapper(wrapped)
        return WrapperSetup(wrapper, wrapped)
    }

    private class TestSharedViewModelWrapper(
        wrapped: TestCoreViewModel,
    ) : SharedViewModelWrapper<TestCoreViewModel>(wrapped) {
        fun triggerOnCleared() {
            onCleared()
        }
    }

    private data class WrapperSetup(
        val wrapper: TestSharedViewModelWrapper,
        val wrapped: TestCoreViewModel,
    )

    private object WrapperTestEvent : UIEvent
}
