package de.stefan.lang.shapebyte.features.workout.history.domain

import app.cash.turbine.test
import de.stefan.lang.shapebyte.di.DPI
import de.stefan.lang.shapebyte.features.core.domain.FeatureId
import de.stefan.lang.shapebyte.shared.featuretoggles.data.FeatureToggle
import de.stefan.lang.shapebyte.shared.featuretoggles.data.FeatureToggleState
import de.stefan.lang.shapebyte.shared.featuretoggles.di.featureToggleDatasourceMock
import de.stefan.lang.shapebyte.shared.loading.data.LoadState
import de.stefan.lang.shapebyte.utils.BaseCoroutineTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertIs

class FetchRecentWorkoutHistoryUseCaseTest : BaseCoroutineTest() {
    private val featureTogglesDCMock get() = DPI.featureToggleDatasourceMock

    @Test
    fun `emit success if feature toggle is enabled`() = test {
        featureTogglesDCMock.setFeatureToggles(
            listOf(
                FeatureToggle(FeatureId.RECENT_HISTORY.name, FeatureToggleState.ENABLED),
            ),
        )

        val sut = createSUT()
        sut.invoke(this).test {
            var item = awaitItem()
            assertIs<LoadState.Loading>(item)

            item = awaitItem()
            val successState = item as LoadState.Success
            assertFalse(successState.data.isEmpty())
            expectNoEvents()
        }
    }

    @Test
    fun `emit failure if feature toggle is disabled`() = test {
        featureTogglesDCMock.setFeatureToggles(
            listOf(
                FeatureToggle(FeatureId.RECENT_HISTORY.name, FeatureToggleState.DISABLED),
            ),
        )

        val sut = createSUT()
        sut.invoke(this).test {
            val item = awaitItem()
            assertIs<LoadState.Loading>(item)

            val failure = awaitItem() as LoadState.Error
            assertEquals(failure.reason, HistoryError.FeatureDisabled)
            expectNoEvents()
        }
    }

    @Test
    fun `emit failure if feature toggle is not set `() = test {
        featureTogglesDCMock.setFeatureToggles(emptyList())

        val sut = createSUT()
        sut.invoke(this).test {
            val item = awaitItem()
            assertIs<LoadState.Loading>(item)

            val failure = awaitItem() as LoadState.Error
            assertEquals(failure.reason, HistoryError.FeatureDisabled)
            expectNoEvents()
        }
    }

    private fun createSUT(): FetchRecentWorkoutHistoryUseCase {
        val retVal = DPI.fetchRecentWorkoutHistoryUseCase()
        return retVal
    }
}
