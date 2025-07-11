package de.stefan.lang.shapebyte.features.workout.workoutDomain

import app.cash.turbine.test
import de.stefan.lang.coreutils.api.progress.Progress
import de.stefan.lang.featureToggles.api.FeatureToggle
import de.stefan.lang.featureToggles.api.LoadFeatureToggleUseCase
import de.stefan.lang.featureToggles.api.FeatureToggleState
import de.stefan.lang.foundationCore.api.loadstate.LoadState
import de.stefan.lang.featureToggles.api.FeatureId
import de.stefan.lang.shapebyte.features.workout.workoutData.mocks.WorkoutHistoryRepository
import de.stefan.lang.shapebyte.features.workout.workoutData.mocks.WorkoutScheduleEntry
import de.stefan.lang.shapebyte.features.workout.workoutDomain.workout.FetchRecentWorkoutHistoryUseCase
import de.stefan.lang.shapebyte.features.workout.workoutDomain.workout.HistoryError
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.datetime.Instant
import org.koin.core.component.get
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertIs

class FetchRecentWorkoutHistoryUseCaseTest : BaseWorkoutDomainTest() {

    private val repository: WorkoutHistoryRepository = mockk(relaxed = true)
    private val loadFeatureToggleUseCase: LoadFeatureToggleUseCase = mockk(relaxed = true)

    @Test
    fun `emit success if feature toggle is enabled`() = test {
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
        val sut = createSUT(isToggleEnabled = false)

        sut.invoke().test {
            val item = awaitItem()
            assertIs<LoadState.Loading>(item)

            val failure = awaitItem() as LoadState.Error
            assertEquals(failure.reason, HistoryError.FeatureDisabled)
            expectNoEvents()
        }
    }

    private fun createSUT(
        isToggleEnabled: Boolean = true
    ): FetchRecentWorkoutHistoryUseCase {
        val ffState = if (isToggleEnabled) {
            FeatureToggleState.ENABLED
        } else {
            FeatureToggleState.DISABLED
        }

        every { loadFeatureToggleUseCase.invoke(any()) } returns flowOf(
            LoadState.Success(
                FeatureToggle(
                    FeatureId.RECENT_HISTORY.name,
                    ffState
                )
            )
        )

        coEvery { repository.historyForDates(any(), any()) } coAnswers {
            val list = listOf(
                WorkoutScheduleEntry(
                    id = "1",
                    name = "Workout 1",
                    date = Instant.parse("2022-01-01T00:00:00Z"),
                    progress = Progress.COMPLETE
                )
            )

            LoadState.Success(list)
        }

        val retVal = FetchRecentWorkoutHistoryUseCase(
            repository = repository,
            logger = get(),
            coroutineContextProviding = get(),
            coroutineScopeProviding = get(),
            loadFeatureToggleUseCase = loadFeatureToggleUseCase,
        )
        return retVal
    }
}
