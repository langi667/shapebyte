package de.stefan.lang.shapebyte.featureToggles.domain

import app.cash.turbine.test
import de.stefan.lang.foundationCore.loadstate.asResultFlow
import de.stefan.lang.shapebyte.featureToggles.BaseFeatureToggleTest
import de.stefan.lang.shapebyte.featureToggles.FeatureTogglesModule
import de.stefan.lang.shapebyte.featureToggles.data.FeatureToggle
import de.stefan.lang.shapebyte.featureToggles.data.FeatureToggleState
import de.stefan.lang.shapebyte.featureToggles.data.impl.FeatureToggleDatasourceMock
import de.stefan.lang.shapebyte.featureToggles.featureToggleDatasourceMock
import kotlin.test.AfterTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class LoadFeatureToggleUseCaseTest : BaseFeatureToggleTest() {
    private val sut: LoadFeatureToggleUseCase = FeatureTogglesModule.loadFeatureToggleUseCase()

    private val datasource: FeatureToggleDatasourceMock
        get() = FeatureTogglesModule.featureToggleDatasourceMock

    @Test
    fun `should emit Success with null if toggle is not present`() = test {
        sut.invoke("some").asResultFlow().test {
            val item = awaitItem()
            assertNull(item.dataOrNull())
            expectNoEvents()
        }
    }

    @Test
    fun `should emit Success with FeatureToggle if toggle is present`() = test {
        val toggleId = "toggle id"
        val toggle = FeatureToggle(toggleId, FeatureToggleState.ENABLED)

        datasource.addFeatureToggle(toggle)

        sut.invoke(toggleId).asResultFlow().test {
            val item = awaitItem()
            assertEquals(toggle, item.dataOrNull())
            expectNoEvents()
        }
    }

    @AfterTest
    fun clearDataSource() {
        datasource.setFeatureToggles(emptyList())
    }
}
