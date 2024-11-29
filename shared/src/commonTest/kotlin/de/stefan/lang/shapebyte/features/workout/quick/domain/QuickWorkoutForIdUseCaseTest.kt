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

class QuickWorkoutForIdUseCaseTest : BaseCoroutineTest() {
    private val featureToggleDataSource get() = DPI.featureToggleDatasourceMock
    private val quickWorkoutsDataSource get() = DPI.quickWorkoutsDatasourceMock

    private val validWorkoutId = 1
    private val invalidWorkoutId = -1

    @Test
    fun `Should return quick workout`() = test {
        featureToggleDataSource.addFeatureToggle(
            FeatureToggle(
                identifier = FeatureId.QUICK_WORKOUTS.name,
                state = FeatureToggleState.ENABLED,
            ),
        )

        val sut = createSUT()
        val expectedData = quickWorkoutsDataSource.workouts.first { it.id == validWorkoutId }

        sut(validWorkoutId).test {
            assertEquals(LoadState.Loading, awaitItem())
            assertEquals(LoadState.Success(expectedData), awaitItem())

            expectNoEvents()
        }
    }

    @Test
    fun `Should return error if workout does not exists`() = test {
        featureToggleDataSource.addFeatureToggle(
            FeatureToggle(
                identifier = FeatureId.QUICK_WORKOUTS.name,
                state = FeatureToggleState.ENABLED,
            ),
        )

        val sut = createSUT()

        sut(invalidWorkoutId).test {
            assertEquals(LoadState.Loading, awaitItem())
            assertEquals(LoadState.Error(QuickWorkoutsError.WorkoutDoesNotExist(invalidWorkoutId)), awaitItem())

            expectNoEvents()
        }
    }

    @Test
    fun `Should return error if feature is disabled`() = test {
        featureToggleDataSource.addFeatureToggle(
            FeatureToggle(
                identifier = FeatureId.QUICK_WORKOUTS.name,
                state = FeatureToggleState.DISABLED,
            ),
        )

        val sut = createSUT()

        sut(validWorkoutId).test {
            assertEquals(LoadState.Loading, awaitItem())
            assertEquals(LoadState.Error(QuickWorkoutsError.FeatureDisabled), awaitItem())

            expectNoEvents()
        }
    }

    private fun createSUT(): QuickWorkoutForIdUseCase = DPI.createQuickWorkoutForIdUseCase()
}
