package de.stefan.lang.shapebyte.features.workout.workoutDomain

import app.cash.turbine.test
import de.stefan.lang.featureToggles.api.FeatureToggle
import de.stefan.lang.featureToggles.api.FeatureToggleLoading
import de.stefan.lang.featureToggles.api.FeatureToggleState
import de.stefan.lang.foundationCore.api.image.ImageResource
import de.stefan.lang.foundationCore.api.loadstate.LoadState
import de.stefan.lang.featureToggles.api.FeatureId
import de.stefan.lang.shapebyte.features.workout.workoutData.mocks.WorkoutType
import de.stefan.lang.shapebyte.features.workout.workoutData.workout.QuickWorkoutsError
import de.stefan.lang.shapebyte.features.workout.workoutData.workout.QuickWorkoutsRepository
import de.stefan.lang.shapebyte.features.workout.workoutData.workout.Workout
import de.stefan.lang.shapebyte.features.workout.workoutDomain.workout.QuickWorkoutForIdUseCase
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import org.koin.core.component.get
import kotlin.test.Test
import kotlin.test.assertEquals

class QuickWorkoutForIdUseCaseTest : BaseWorkoutDomainTest() {
    private val repository: QuickWorkoutsRepository = mockk(relaxed = true)
    private val featureToggleLoading: FeatureToggleLoading = mockk(relaxed = true)

    private val validWorkoutId = 1
    private val invalidWorkoutId = -1

    private val workouts = listOf(
        Workout(
            id = 1,
            name = "Interval",
            shortDescription = "Quick ",
            image = ImageResource(id = "sprints.png"),
            type = WorkoutType.Timed.Interval(5, 5, 2),
        ),
        Workout(
            id = 2,
            name = "HIIT Core",
            shortDescription = "20 min, Core, Legs",
            image = ImageResource(id = "sprints.png"),
            type = WorkoutType.Timed.Interval(30, 30, 20),
        ),
        Workout(
            id = 3,
            name = "Sets & Reps",
            shortDescription = "3 Sets 6 exercises",
            image = ImageResource(id = "sprints.png"),
            type = WorkoutType.Timed.Interval(30, 30, 20),
        ),
        Workout(
            id = 4,
            name = "Warmup",
            shortDescription = "40, min, Interval 15 sec.",
            image = ImageResource(id = "sprints.png"),
            type = WorkoutType.Timed.Interval(30, 30, 20),
        ),
        Workout(
            id = 5,
            name = "Cooldown",
            shortDescription = "20, min, Interval 1 Minute",
            image = ImageResource(id = "sprints.png"),
            type = WorkoutType.Timed.Interval(30, 30, 20),
        ),
    )

    @Test
    fun `Should return quick workout`() = test {
        val sut = createSUT(workoutId = validWorkoutId)
        val expectedData = workouts.first { it.id == validWorkoutId }

        sut(validWorkoutId).test {
            assertEquals(LoadState.Loading, awaitItem())
            assertEquals(LoadState.Success(expectedData), awaitItem())

            expectNoEvents()
        }
    }

    @Test
    fun `Should return error if workout does not exists`() = test {
        val sut = createSUT(workoutId = invalidWorkoutId)

        sut(invalidWorkoutId).test {
            assertEquals(LoadState.Loading, awaitItem())
            assertEquals(
                LoadState.Error(
                    QuickWorkoutsError.WorkoutDoesNotExist(
                        invalidWorkoutId
                    )
                ), awaitItem()
            )

            expectNoEvents()
        }
    }

    @Test
    fun `Should return error if feature is disabled`() = test {
        val sut = createSUT(isToggleEnabled = false, workoutId = validWorkoutId)

        sut(validWorkoutId).test {
            assertEquals(LoadState.Loading, awaitItem())
            assertEquals(LoadState.Error(QuickWorkoutsError.FeatureDisabled), awaitItem())

            expectNoEvents()
        }
    }

    private fun createSUT(
        isToggleEnabled: Boolean = true,
        workoutId: Int
    ): QuickWorkoutForIdUseCase {

        val ffState = if (isToggleEnabled) {
            FeatureToggleState.ENABLED
        } else {
            FeatureToggleState.DISABLED
        }

        every { featureToggleLoading.invoke(any()) } returns flowOf(
            LoadState.Success(
                FeatureToggle(
                    FeatureId.QUICK_WORKOUTS.name,
                    ffState
                )
            )
        )

        coEvery { repository.fetchQuickWorkouts() } returns LoadState.Success(workouts)
        coEvery { repository.workoutForId(workoutId) } coAnswers {
            val workout = workouts.firstOrNull { it.id == workoutId }

            if (workout != null) {
                return@coAnswers LoadState.Success(
                    workouts.first { it.id == workoutId }
                )
            } else {
                return@coAnswers LoadState.Error(
                    QuickWorkoutsError.WorkoutDoesNotExist(
                        workoutId
                    )
                )
            }
        }

        return QuickWorkoutForIdUseCase(
            repository = repository,
            logger = WorkoutDomainModule.get(),
            coroutineContextProvider = WorkoutDomainModule.get(),
            coroutineScopeProvider = WorkoutDomainModule.get(),
            featureToggleLoading = featureToggleLoading,
        )
    }
}
