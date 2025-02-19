package de.stefan.lang.shapebyte.features.workout.history.domain

import app.cash.turbine.test
import de.stefan.lang.shapebyte.featureToggles.data.FeatureToggle
import de.stefan.lang.shapebyte.featureToggles.data.FeatureToggleDatasource
import de.stefan.lang.shapebyte.featureToggles.data.FeatureToggleState
import de.stefan.lang.shapebyte.featureToggles.data.impl.FeatureToggleDatasourceMock
import de.stefan.lang.foundationCore.loadstate.LoadState
import de.stefan.lang.shapebyte.SharedModule
import de.stefan.lang.shapebyte.featureToggles.FeatureId
import de.stefan.lang.shapebyte.features.BaseWorkoutFeatureTest
import org.koin.core.component.get
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertIs

class FetchRecentWorkoutHistoryUseCaseTest : BaseWorkoutFeatureTest() {
    private val featureTogglesDCMock: FeatureToggleDatasourceMock
        get() = SharedModule.get<FeatureToggleDatasource>() as FeatureToggleDatasourceMock

    @Test
    fun `emit success if feature toggle is enabled`() = test {
        featureTogglesDCMock.setFeatureToggles(
            listOf(
                FeatureToggle(FeatureId.RECENT_HISTORY.name, FeatureToggleState.ENABLED),
            ),
        )

        val sut = createSUT()
        sut.invoke().test {
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
        sut.invoke().test {
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
        sut.invoke().test {
            val item = awaitItem()
            assertIs<LoadState.Loading>(item)

            val failure = awaitItem() as LoadState.Error
            assertEquals(failure.reason, HistoryError.FeatureDisabled)
            expectNoEvents()
        }
    }

    private fun createSUT(): FetchRecentWorkoutHistoryUseCase {
        val retVal = SharedModule.fetchRecentWorkoutHistoryUseCase()
        return retVal
    }
}
