package de.stefan.lang.shapebyte.features.workout.quick.domain

import app.cash.turbine.test
import de.stefan.lang.shapebyte.di.DPI
import de.stefan.lang.shapebyte.features.core.domain.FeatureId
import de.stefan.lang.shapebyte.shared.featuretoggles.data.FeatureToggle
import de.stefan.lang.shapebyte.shared.featuretoggles.data.FeatureToggleState
import de.stefan.lang.shapebyte.shared.featuretoggles.di.featureToggleDatasourceMock
import de.stefan.lang.shapebyte.shared.featuretoggles.di.quickWorkoutsDatasourceMock
import de.stefan.lang.shapebyte.shared.loading.data.LoadState
import de.stefan.lang.shapebyte.utils.BaseCoroutineTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs

class FetchQuickWorkoutsUseCaseTest : BaseCoroutineTest() {
    private val featureTogglesDCMock get() = DPI.featureToggleDatasourceMock
    private val quickWorkoutsDatasourceMock get() = DPI.quickWorkoutsDatasourceMock

    @Test
    fun `emit Success if Feature Toggle is enabled`() = test {
        featureTogglesDCMock.setFeatureToggles(
            listOf(
                FeatureToggle(FeatureId.QUICK_WORKOUTS.name, FeatureToggleState.ENABLED),
            ),
        )

        val sut = createSUT()
        sut.invoke(this).test {
            var item = awaitItem()
            assertIs<LoadState.Loading>(item)

            item = awaitItem()
            val successState = item as LoadState.Success
            assertEquals(quickWorkoutsDatasourceMock.workouts, successState.data)
            expectNoEvents()
        }
    }

    @Test
    fun `emit failure if Feature Toggle is disabled`() = test {
        featureTogglesDCMock.setFeatureToggles(
            listOf(
                FeatureToggle(FeatureId.QUICK_WORKOUTS.name, FeatureToggleState.DISABLED),
            ),
        )

        val sut = createSUT()
        sut.invoke(this).test {
            val item = awaitItem()
            assertIs<LoadState.Loading>(item)

            val failure = awaitItem() as LoadState.Error
            assertEquals(QuickWorkoutsError.FeatureDisabled, failure.reason)
            expectNoEvents()
        }
    }

    @Test
    fun `emit failure if Feature Toggle is not set`() = test {
        featureTogglesDCMock.setFeatureToggles(emptyList())

        val sut = createSUT()
        sut.invoke(this).test {
            val item = awaitItem()
            assertIs<LoadState.Loading>(item)

            val failure = awaitItem() as LoadState.Error
            assertEquals(QuickWorkoutsError.FeatureDisabled, failure.reason)
            expectNoEvents()
        }
    }

    private fun createSUT(): FetchQuickWorkoutsUseCase {
        val retVal = DPI.fetchQuickWorkoutsUseCase()
        return retVal
    }
}
