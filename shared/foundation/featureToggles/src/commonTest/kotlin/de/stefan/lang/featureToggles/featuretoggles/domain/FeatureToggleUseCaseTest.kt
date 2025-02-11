package de.stefan.lang.featureToggles.featuretoggles.domain

import app.cash.turbine.test
import de.stefan.lang.coretest.CoreTest
import de.stefan.lang.featureToggles.FeatureTogglesModule
import de.stefan.lang.featureToggles.data.FeatureToggle
import de.stefan.lang.featureToggles.data.FeatureToggleState
import de.stefan.lang.featureToggles.data.impl.FeatureToggleDatasourceMock
import de.stefan.lang.featureToggles.data.impl.FeatureToggleError
import de.stefan.lang.featureToggles.domain.FeatureToggleUseCase
import de.stefan.lang.featureToggles.featuretoggles.di.featureToggleDatasourceMock
import de.stefan.lang.foundationCore.loadstate.LoadState
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertIs
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

class FeatureToggleUseCaseTest : CoreTest() {
    private val feature = "QUICK_WORKOUTS"
    private val sut: FeatureToggleUseCase by lazy { FeatureTogglesModule.featureToggleUseCase(featureId = feature) }

    private val datasource: FeatureToggleDatasourceMock
        get() = FeatureTogglesModule.featureToggleDatasourceMock

    @Test
    fun `should return null if toggle is not set`() = test {
        datasource.setFeatureToggles(emptyList())
        sut.invoke().test {
            val item = awaitItem() as LoadState.Error
            assertNull(item.dataOrNull())
            assertIs<FeatureToggleError.NotFound>(item.errorOrNull())

            expectNoEvents()
        }
    }

    @Test
    fun `should return false if toggle is not set`() = test {
        datasource.setFeatureToggles(emptyList())
        sut.isEnabled.test {
            assertFalse(awaitItem())
            expectNoEvents()
        }
    }

    @Test
    fun `should return toggle is set`() = test {
        datasource.setFeatureToggles(
            listOf(
                FeatureToggle(feature, FeatureToggleState.ENABLED),
            ),
        )
        sut.invoke().test {
            val item = awaitItem() as LoadState.Success
            assertNotNull(item.data)
            expectNoEvents()
        }
    }

    @Test
    fun `should return true if toggle is set and enabled`() = test {
        datasource.setFeatureToggles(
            listOf(
                FeatureToggle(feature, FeatureToggleState.ENABLED),
            ),
        )
        sut.isEnabled.test {
            assertTrue(awaitItem())
            expectNoEvents()
        }
    }

    @Test
    fun `should return false if toggle is set and disabled`() = test {
        datasource.setFeatureToggles(
            listOf(
                FeatureToggle(feature, FeatureToggleState.DISABLED),
            ),
        )
        sut.isEnabled.test {
            assertFalse(awaitItem())
            expectNoEvents()
        }
    }
}
