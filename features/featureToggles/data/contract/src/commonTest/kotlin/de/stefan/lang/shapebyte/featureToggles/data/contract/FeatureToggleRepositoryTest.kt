package de.stefan.lang.shapebyte.featureToggles.data.contract

import de.stefan.lang.foundation.core.contract.loadstate.LoadState
import de.stefan.lang.utils.logging.contract.Logging
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class FeatureToggleRepositoryTest {
    private val logging = object : Logging {
        override fun log(tag: String, message: String) = Unit
        override fun logError(tag: String, throwable: Throwable, message: String?) = Unit
    }

    private val datasource = FakeFeatureToggleDatasource()

    private val sut = FeatureToggleRepository(
        logger = logging,
        dataSource = datasource,
    )

    @Test
    fun `should return Success with null if toggle is not present`() = runRepositoryTest {
        val item = sut.fetchFeatureToggle("some")
        if (item is LoadState.Success) {
            assertNull(item.data)
        } else {
            throw AssertionError("Expected success state")
        }
    }

    @Test
    fun `should emit Success with FeatureToggle if toggle is present`() = runRepositoryTest {
        val toggleId = "toggle id"
        val toggle = FeatureToggle(toggleId, FeatureToggleState.ENABLED)

        datasource.addFeatureToggle(toggle)
        val item = sut.fetchFeatureToggle(toggleId)
        if (item is LoadState.Success) {
            assertEquals(toggle, item.data)
        } else {
            throw AssertionError("Expected success state")
        }
    }

    private fun runRepositoryTest(block: suspend () -> Unit) {
        runTest { block() }
    }

    private class FakeFeatureToggleDatasource : FeatureToggleDatasource {
        private val toggles = mutableListOf<FeatureToggle>()

        override suspend fun fetchFeatureToggle(identifier: String): LoadState.Result<FeatureToggle> {
            return LoadState.Success(toggles.firstOrNull { it.identifier == identifier })
        }

        fun addFeatureToggle(featureToggle: FeatureToggle) {
            toggles += featureToggle
        }
    }
}
