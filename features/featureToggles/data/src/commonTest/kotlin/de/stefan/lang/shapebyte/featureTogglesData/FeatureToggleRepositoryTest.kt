package de.stefan.lang.shapebyte.featureTogglesData

import de.stefan.lang.shapebyte.featureTogglesData.impl.FeatureToggleDatasourceMock
import kotlin.test.AfterTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class FeatureToggleRepositoryTest : BaseFeatureToggleDataTest() {
    private val sut
        get() = FeatureTogglesDataModule.featureToggleRepository()

    private val datasource: FeatureToggleDatasourceMock
        get() = FeatureTogglesDataModule.featureToggleDatasourceMock

    @Test
    fun `evaluate Mocks`() = test {
        assertEquals(datasource, sut.dataSource)
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
