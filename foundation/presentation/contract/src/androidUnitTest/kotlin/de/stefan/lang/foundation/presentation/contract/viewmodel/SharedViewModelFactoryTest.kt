package de.stefan.lang.foundation.presentation.contract.viewmodel

import androidx.lifecycle.ViewModel
import de.stefan.lang.coretest.CoreTest
import de.stefan.lang.coroutines.CoroutinesModule
import de.stefan.lang.foundation.presentation.contract.state.UIState
import de.stefan.lang.utils.logging.LoggingModule
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
class SharedViewModelFactoryTest : CoreTest() {

    override val testModules = super.testModules + listOf(
        LoggingModule.testDiModule,
        CoroutinesModule.testDiModule,
    )

    @Test
    fun create_wrapsProvidedSharedViewModel() = test {
        var invocationCount = 0
        val providedViewModel = TestSharedViewModelBase(
            logger = LoggingModule.logger(),
            coroutineContextProvider = CoroutinesModule.coroutineContextProvider(),
            coroutineScopeProvider = CoroutinesModule.coroutineScopeProvider(),
        )
        val onClearedCallback = mockk<() -> Unit>(relaxed = true)
        providedViewModel.onClearedCallback = onClearedCallback

        val factory = SharedViewModelFactory {
            invocationCount++
            providedViewModel
        }

        val result = factory.create(ViewModel::class.java)

        assertTrue(result is SharedViewModel)
        assertTrue(result is SharedViewModelWrapper<*>)
        assertEquals(1, invocationCount)

        val wrapper = result as SharedViewModel

        wrapper.updateState(UIState.Loading)
        assertEquals(UIState.Loading, providedViewModel.state.value)

        wrapper.clear()
        verify(exactly = 1) { onClearedCallback.invoke() }
    }
}
