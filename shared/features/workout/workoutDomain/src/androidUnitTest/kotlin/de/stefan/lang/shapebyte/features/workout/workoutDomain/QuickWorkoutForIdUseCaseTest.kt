package de.stefan.lang.shapebyte.features.workout.workoutDomain

import app.cash.turbine.test
import de.stefan.lang.foundationCore.api.loadstate.LoadState
import de.stefan.lang.shapebyte.featureToggles.FeatureId
import de.stefan.lang.shapebyte.featureToggles.FeatureTogglesModule
import de.stefan.lang.shapebyte.featureTogglesData.FeatureToggle
import de.stefan.lang.shapebyte.featureTogglesData.FeatureToggleDatasource
import de.stefan.lang.shapebyte.featureTogglesData.FeatureToggleState
import de.stefan.lang.shapebyte.featureTogglesData.impl.FeatureToggleDatasourceMock
import de.stefan.lang.shapebyte.features.workout.workoutData.workout.QuickWorkoutsDatasource
import de.stefan.lang.shapebyte.features.workout.workoutData.mocks.QuickWorkoutsDatasourceMocks
import de.stefan.lang.shapebyte.features.workout.workoutData.workout.QuickWorkoutsError
import de.stefan.lang.shapebyte.features.workout.workoutDomain.workout.QuickWorkoutForIdUseCase
import org.koin.core.component.get
import kotlin.test.Test
import kotlin.test.assertEquals

class QuickWorkoutForIdUseCaseTest : BaseWorkoutDomainTest() {

    // TODO: use mockk instead of manual mock
    private val featureToggleDataSource: FeatureToggleDatasourceMock
        get() = FeatureTogglesModule.get<FeatureToggleDatasource>() as FeatureToggleDatasourceMock

    // TODO: use mockk instead of manual mock
    private val quickWorkoutsDataSource: QuickWorkoutsDatasourceMocks
        get() = WorkoutDomainModule.get<QuickWorkoutsDatasource>() as QuickWorkoutsDatasourceMocks

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
            assertEquals(
                LoadState.Error(
                QuickWorkoutsError.WorkoutDoesNotExist(
                    invalidWorkoutId
                )
            ), awaitItem())

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

    private fun createSUT(): QuickWorkoutForIdUseCase = WorkoutDomainModule.createQuickWorkoutForIdUseCase()
}
