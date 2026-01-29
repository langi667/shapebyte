package de.stefan.lang.shapebyte.featureToggles.data.contract

import de.stefan.lang.coretest.CoreTest
import de.stefan.lang.foundation.core.contract.loadstate.LoadState
import de.stefan.lang.utils.logging.LoggingModule
import org.koin.core.module.Module
import org.koin.test.KoinTest
import kotlin.test.Test
import kotlin.test.assertEquals

class FeatureToggleRepositoryTest : CoreTest(), KoinTest {

    override val testModules: List<Module> = listOf(
        LoggingModule.testModules,
    )

    private val datasource = FakeFeatureToggleDatasource()

    private val sut by lazy {
        FeatureToggleRepository(
            logger = LoggingModule.logger(),
            dataSource = datasource,
        )
    }

    @Test
    fun `should return Error toggle is not present`() = runRepositoryTest {
        val item = sut.fetchFeatureToggle("some")
        assertEquals(LoadState.Error(FeatureToggleError.NotFound("some")), item)
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
        test { block() }
    }

    private class FakeFeatureToggleDatasource : FeatureToggleDatasource {
        private val toggles = mutableListOf<FeatureToggle>()

        override suspend fun fetchFeatureToggle(identifier: String): LoadState.Result<FeatureToggle> {
            val toggle = toggles.firstOrNull { it.identifier == identifier }

            return if (toggle == null) {
                LoadState.Error(FeatureToggleError.NotFound(identifier))
            } else {
                LoadState.Success(toggle)
            }
        }

        fun addFeatureToggle(featureToggle: FeatureToggle) {
            toggles += featureToggle
        }
    }
}
