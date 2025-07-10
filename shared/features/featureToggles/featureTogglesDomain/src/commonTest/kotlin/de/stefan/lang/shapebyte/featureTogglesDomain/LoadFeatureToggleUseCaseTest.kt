package de.stefan.lang.shapebyte.featureTogglesDomain

import app.cash.turbine.test
import de.stefan.lang.foundationCore.api.loadstate.asResultFlow
import de.stefan.lang.shapebyte.featureTogglesData.FeatureToggle
import de.stefan.lang.shapebyte.featureTogglesData.FeatureToggleState
import de.stefan.lang.shapebyte.featureTogglesData.FeatureTogglesDataModule
import de.stefan.lang.shapebyte.featureTogglesData.impl.FeatureToggleDatasourceMock
import kotlin.test.AfterTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class LoadFeatureToggleUseCaseTest : BaseFeatureToggleDomainTest() {
    private val sut: LoadFeatureToggleUseCase
        get() = FeatureTogglesDomainModule.loadFeatureToggleUseCase()

    private val datasource: FeatureToggleDatasourceMock
        get() = FeatureTogglesDataModule.featureToggleDatasourceMock

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
