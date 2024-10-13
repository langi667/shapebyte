package de.stefan.lang.shapebyte.shared.featuretoggles.data

import app.cash.turbine.test
import de.stefan.lang.shapebyte.di.CommonMainModule
import de.stefan.lang.shapebyte.shared.featuretoggles.data.impl.FeatureToggleDatasourceMock
import de.stefan.lang.shapebyte.shared.featuretoggles.di.featureToggleDatasourceMock
import de.stefan.lang.shapebyte.utils.BaseCoroutineTest
import org.koin.test.inject
import kotlin.test.AfterTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class FeatureToggleRepositoryTest : BaseCoroutineTest() {
    private val sut: FeatureToggleRepository by inject()

    private val datasource: FeatureToggleDatasourceMock
        get() = CommonMainModule.featureToggleDatasourceMock

    @Test
    fun `evaluate Mocks`() = test {
        assertEquals(datasource, sut.datasource)
    }

    @Test
    fun `should emit Success with null if toggle is not present`() = test {
        sut.fetchFeatureToggle("some").test {
            val item = awaitItem()
            assertNull(item.dataOrNull())
            awaitComplete()
        }
    }

    @Test
    fun `should emit Success with FeatureToggle if toggle is present`() = test {
        val toggleId = "toggle id"
        val toggle = FeatureToggle(toggleId, FeatureToggleState.ENABLED)

        datasource.addFeatureToggle(toggle)

        sut.fetchFeatureToggle(toggleId).test {
            val item = awaitItem()
            assertEquals(toggle, item.dataOrNull())
            awaitComplete()
        }
    }

    @AfterTest
    fun clearDataSource() {
        datasource.setFeatureToggles(emptyList())
    }
}
