package de.stefan.lang.shapebyte.shared.featuretoggles.data

import app.cash.turbine.test
import de.stefan.lang.shapebyte.di.DPI
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
        get() = DPI.featureToggleDatasourceMock

    @Test
    fun `evaluate Mocks`() = test {
        assertEquals(datasource, sut.defaultFeatureTogglesDatasource)
    }

    @Test
    fun `should return Success with null if toggle is not present`() = test {
        val item = sut.fetchFeatureToggle("some")
        assertNull(item.dataOrNull())
    }

    @Test
    fun `should emit Success with FeatureToggle if toggle is present`() = test {
        val toggleId = "toggle id"
        val toggle = FeatureToggle(toggleId, FeatureToggleState.ENABLED)

        datasource.addFeatureToggle(toggle)
        val item = sut.fetchFeatureToggle(toggleId)
        assertEquals(toggle, item.dataOrNull())
    }

    @AfterTest
    fun clearDataSource() {
        datasource.setFeatureToggles(emptyList())
    }
}
