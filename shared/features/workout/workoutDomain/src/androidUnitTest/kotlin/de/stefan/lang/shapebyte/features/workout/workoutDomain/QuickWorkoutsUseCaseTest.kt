package de.stefan.lang.shapebyte.features.workout.workoutDomain

import app.cash.turbine.test
import de.stefan.lang.shapebyte.featureToggles.data.FeatureToggle
import de.stefan.lang.shapebyte.featureToggles.data.FeatureToggleDatasource
import de.stefan.lang.shapebyte.featureToggles.data.FeatureToggleState
import de.stefan.lang.shapebyte.featureToggles.data.impl.FeatureToggleDatasourceMock
import de.stefan.lang.foundationCore.loadstate.LoadState
import de.stefan.lang.shapebyte.featureToggles.FeatureId
import de.stefan.lang.shapebyte.featureToggles.FeatureTogglesModule
import de.stefan.lang.shapebyte.features.workout.workoutData.QuickWorkoutsDatasource
import de.stefan.lang.shapebyte.features.workout.workoutData.mocks.QuickWorkoutsDatasourceMocks
import de.stefan.lang.shapebyte.features.workout.workoutData.QuickWorkoutsError
import org.koin.core.component.get
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs

class QuickWorkoutsUseCaseTest : BaseWorkoutDomainTest() {
    private val featureTogglesDCMock: FeatureToggleDatasourceMock
        get() = FeatureTogglesModule.get<FeatureToggleDatasource>() as FeatureToggleDatasourceMock

    private val quickWorkoutsDatasourceMock: QuickWorkoutsDatasourceMocks
        get() = WorkoutDomainModule.get<QuickWorkoutsDatasource>() as QuickWorkoutsDatasourceMocks

    @Test
    fun `emit Success if Feature Toggle is enabled`() = test {
        featureTogglesDCMock.setFeatureToggles(
            listOf(
                FeatureToggle(FeatureId.QUICK_WORKOUTS.name, FeatureToggleState.ENABLED),
            ),
        )

        val sut = createSUT()
        sut.invoke().test {
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
        sut.invoke().test {
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
        sut.invoke().test {
            val item = awaitItem()
            assertIs<LoadState.Loading>(item)

            val failure = awaitItem() as LoadState.Error
            assertEquals(QuickWorkoutsError.FeatureDisabled, failure.reason)
            expectNoEvents()
        }
    }

    private fun createSUT(): QuickWorkoutsUseCase {
        val retVal = WorkoutDomainModule.quickWorkoutsUseCase()
        return retVal
    }
}
