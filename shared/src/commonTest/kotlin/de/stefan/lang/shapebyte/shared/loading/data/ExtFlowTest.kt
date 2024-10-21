package de.stefan.lang.shapebyte.shared.loading.data

import app.cash.turbine.test
import de.stefan.lang.shapebyte.utils.BaseCoroutineTest
import kotlinx.coroutines.flow.flowOf
import kotlin.test.Test
import kotlin.test.assertEquals

class ExtFlowTest : BaseCoroutineTest() {
    @Test
    fun `asDataFlow should return data from flow`() = test {
        // Given
        val data = "data"
        val flow = flowOf(LoadState.Success(data))

        flow.asDataFlow().test {
            val item = awaitItem()
            assertEquals(data, item)
            awaitComplete()
        }
    }

    @Test
    fun `asDataFlow should not emit if no Success state`() = test {
        flowOf(LoadState.Loading).asDataFlow().test {
            awaitComplete()
        }

        flowOf(LoadState.Error(Exception())).asDataFlow().test {
            awaitComplete()
        }
    }
}
