package de.stefan.lang.shapebyte.features.workout.quick

import app.cash.turbine.test
import de.stefan.lang.coretest.CoreTest
import de.stefan.lang.shapebyte.featureToggles.data.contract.FeatureToggle
import de.stefan.lang.shapebyte.featureToggles.data.contract.FeatureToggleState
import de.stefan.lang.shapebyte.featureTogglesDomain.contract.LoadFeatureToggleUseCase
import de.stefan.lang.foundation.core.contract.image.ImageResource
import de.stefan.lang.foundation.core.contract.loadstate.LoadState
import de.stefan.lang.shapebyte.featureToggles.data.contract.FeatureId
import de.stefan.lang.shapebyte.features.workout.BaseTest
import de.stefan.lang.shapebyte.features.workout.data.contract.Workout
import de.stefan.lang.shapebyte.features.workout.data.contract.WorkoutType
import de.stefan.lang.shapebyte.features.workout.data.contract.quick.QuickWorkoutsError
import de.stefan.lang.shapebyte.features.workout.domain.contract.workout.quick.QuickWorkoutsUseCase
import de.stefan.lang.shapebyte.features.workout.data.contract.quick.QuickWorkoutsRepository
import de.stefan.lang.shapebyte.features.workout.domain.WorkoutDomainModule
import de.stefan.lang.shapebyte.features.workout.domain.implementation.workout.quick.QuickWorkoutsUseCaseImpl
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import org.koin.core.component.get
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs

internal class QuickWorkoutsUseCaseTest : BaseTest() {
    private val quickWorkoutsRepository: QuickWorkoutsRepository = mockk(relaxed = true)
    private val loadFeatureToggleUseCase: LoadFeatureToggleUseCase = mockk(relaxed = true)

    private val workouts = listOf(
        Workout(
            id = 1,
            name = "Interval",
            shortDescription = "Quick ",
            image = ImageResource(id = "Sprints.png"),
            type = WorkoutType.Timed.Interval(5, 5, 2),
        ),
        Workout(
            id = 2,
            name = "HIIT Core",
            shortDescription = "20 min, Core, Legs",
            image = ImageResource(id = "Sprints.png"),
            type = WorkoutType.Timed.Interval(30, 30, 20),
        ),
        Workout(
            id = 3,
            name = "Sets & Reps",
            shortDescription = "3 Sets 6 exercises",
            image = ImageResource(id = "Sprints.png"),
            type = WorkoutType.Timed.Interval(30, 30, 20),
        ),
        Workout(
            id = 4,
            name = "Warmup",
            shortDescription = "40, min, Interval 15 sec.",
            image = ImageResource(id = "Sprints.png"),
            type = WorkoutType.Timed.Interval(30, 30, 20),
        ),
        Workout(
            id = 5,
            name = "Cooldown",
            shortDescription = "20, min, Interval 1 Minute",
            image = ImageResource(id = "Sprints.png"),
            type = WorkoutType.Timed.Interval(30, 30, 20),
        ),
    )

    @Test
    fun `emit Success if Feature Toggle is enabled`() = test {
        val sut = createSUT()

        sut.invoke().test {
            var item = awaitItem()
            assertIs<LoadState.Loading>(item)

            item = awaitItem()
            val successState = item as LoadState.Success
            assertEquals(workouts, successState.data)
            expectNoEvents()
        }
    }

    @Test
    fun `emit failure if Feature Toggle is disabled`() = test {
        val sut = createSUT(isToggleEnabled = false)
        sut.invoke().test {
            val item = awaitItem()
            assertIs<LoadState.Loading>(item)

            val failure = awaitItem() as LoadState.Error
            assertEquals(QuickWorkoutsError.FeatureDisabled, failure.reason)
            expectNoEvents()
        }
    }

    private fun createSUT(
        isToggleEnabled: Boolean = true,
        workouts: List<Workout> = this.workouts
    ): QuickWorkoutsUseCase {

        val ffState = if (isToggleEnabled) {
            FeatureToggleState.ENABLED
        } else {
            FeatureToggleState.DISABLED
        }

        every { loadFeatureToggleUseCase.invoke(any()) } returns flowOf(
            LoadState.Success(
                FeatureToggle(
                    FeatureId.QUICK_WORKOUTS.name,
                    ffState
                )
            )
        )

        coEvery { quickWorkoutsRepository.fetchQuickWorkouts() } returns LoadState.Success(workouts)

        val retVal = QuickWorkoutsUseCaseImpl(
            repository =  quickWorkoutsRepository,
            scopeProvider = WorkoutDomainModule.get(),
            dispatcherProvider = WorkoutDomainModule.get(),
            logger = WorkoutDomainModule.get(),
            loadFeatureToggleUseCase = loadFeatureToggleUseCase,
        )

        return retVal
    }
}
