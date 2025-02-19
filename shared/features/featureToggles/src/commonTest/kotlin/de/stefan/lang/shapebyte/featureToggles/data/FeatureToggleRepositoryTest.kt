package de.stefan.lang.shapebyte.featureToggles.data

import de.stefan.lang.shapebyte.featureToggles.BaseFeatureToggleTest
import de.stefan.lang.shapebyte.featureToggles.FeatureTogglesModule
import de.stefan.lang.shapebyte.featureToggles.data.impl.FeatureToggleDatasourceMock
import de.stefan.lang.shapebyte.featureToggles.featureToggleDatasourceMock
import de.stefan.lang.shapebyte.featureToggles.featureToggleRepository
import kotlin.test.AfterTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class FeatureToggleRepositoryTest : BaseFeatureToggleTest() {
    private val sut = FeatureTogglesModule.featureToggleRepository

    private val datasource: FeatureToggleDatasourceMock
        get() = FeatureTogglesModule.featureToggleDatasourceMock

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
