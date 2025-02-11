package de.stefan.lang.featureToggles.featuretoggles.domain

import app.cash.turbine.test
import de.stefan.lang.coretest.CoreTest
import de.stefan.lang.featureToggles.FeatureTogglesModule
import de.stefan.lang.featureToggles.data.FeatureToggle
import de.stefan.lang.featureToggles.data.FeatureToggleState
import de.stefan.lang.featureToggles.data.impl.FeatureToggleDatasourceMock
import de.stefan.lang.featureToggles.domain.LoadFeatureToggleUseCase
import de.stefan.lang.featureToggles.featuretoggles.di.featureToggleDatasourceMock
import de.stefan.lang.foundationCore.loadstate.asResultFlow
import kotlin.test.AfterTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class LoadFeatureToggleUseCaseTest : CoreTest() {
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
