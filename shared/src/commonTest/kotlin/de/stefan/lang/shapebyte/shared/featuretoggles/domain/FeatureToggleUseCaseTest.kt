package de.stefan.lang.shapebyte.shared.featuretoggles.domain

import app.cash.turbine.test
import de.stefan.lang.shapebyte.di.DPI
import de.stefan.lang.shapebyte.features.core.domain.FeatureId
import de.stefan.lang.shapebyte.shared.featuretoggles.data.FeatureToggle
import de.stefan.lang.shapebyte.shared.featuretoggles.data.FeatureToggleState
import de.stefan.lang.shapebyte.shared.featuretoggles.data.impl.FeatureToggleDatasourceMock
import de.stefan.lang.shapebyte.shared.featuretoggles.data.impl.FeatureToggleError
import de.stefan.lang.shapebyte.shared.featuretoggles.di.featureToggleDatasourceMock
import de.stefan.lang.shapebyte.shared.loading.data.LoadState
import de.stefan.lang.shapebyte.utils.BaseCoroutineTest
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertIs
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

class FeatureToggleUseCaseTest : BaseCoroutineTest() {
    private val feature = FeatureId.QUICK_WORKOUTS
    private val sut: FeatureToggleUseCase by lazy { DPI.featureToggleUseCase(featureId = feature.name) }

    private val datasource: FeatureToggleDatasourceMock
        get() = DPI.featureToggleDatasourceMock

    @Test
    fun `should return null if toggle is not set`() = test {
        datasource.setFeatureToggles(emptyList())
        sut.invoke().test {
            val item = awaitItem() as LoadState.Error
            assertNull(item.dataOrNull())
            assertIs<FeatureToggleError.NotFound>(item.errorOrNull())

            awaitComplete()
        }
    }

    @Test
    fun `should return false if toggle is not set`() = test {
        datasource.setFeatureToggles(emptyList())
        sut.isEnabled.test {
            assertFalse(awaitItem())
            awaitComplete()
        }
    }

    @Test
    fun `should return toggle is set`() = test {
        datasource.setFeatureToggles(
            listOf(
                FeatureToggle(feature.name, FeatureToggleState.ENABLED),
            ),
        )
        sut.invoke().test {
            val item = awaitItem() as LoadState.Success
            assertNotNull(item.data)
            awaitComplete()
        }
    }

    @Test
    fun `should return true if toggle is set and enabled`() = test {
        datasource.setFeatureToggles(
            listOf(
                FeatureToggle(feature.name, FeatureToggleState.ENABLED),
            ),
        )
        sut.isEnabled.test {
            assertTrue(awaitItem())
            awaitComplete()
        }
    }

    @Test
    fun `should return false if toggle is set and disabled`() = test {
        datasource.setFeatureToggles(
            listOf(
                FeatureToggle(feature.name, FeatureToggleState.DISABLED),
            ),
        )
        sut.isEnabled.test {
            assertFalse(awaitItem())
            awaitComplete()
        }
    }
}
