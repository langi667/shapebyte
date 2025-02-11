package de.stefan.lang.foundationCore.loadstate

import app.cash.turbine.test
import de.stefan.lang.coretest.CoreTest
import kotlinx.coroutines.flow.flowOf
import kotlin.test.Test
import kotlin.test.assertEquals

class ExtFlowTest : CoreTest() {
    @Test
    fun `asDataFlow should return data from flow`() = test {
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

    @Test
    fun `asResultFlow should emit in case of error`() = test {
        val error = Exception()
        flowOf(LoadState.Error(error)).asResultFlow().test {
            val item = awaitItem()
            assertEquals(LoadState.Error(error), item)
            awaitComplete()
        }
    }

    @Test
    fun `asResultFlow should emit in case of data`() = test {
        val loadState = LoadState.Success("data")
        val flow = flowOf(loadState)

        flow.asResultFlow().test {
            val item = awaitItem()
            assertEquals(loadState, item)
            awaitComplete()
        }
    }

    @Test
    fun `asResultFlow should not emit in case of Loading`() = test {
        flowOf(LoadState.Loading).asResultFlow().test {
            awaitComplete()
        }
    }
}
