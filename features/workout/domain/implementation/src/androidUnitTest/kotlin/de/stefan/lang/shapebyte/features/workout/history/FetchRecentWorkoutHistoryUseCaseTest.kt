package de.stefan.lang.shapebyte.features.workout.history

import BaseTest
import app.cash.turbine.test
import de.stefan.lang.coreutils.contract.progress.Progress
import de.stefan.lang.foundation.core.contract.loadstate.LoadState
import de.stefan.lang.shapebyte.featureToggles.data.contract.FeatureId
import de.stefan.lang.shapebyte.featureToggles.data.contract.FeatureToggle
import de.stefan.lang.shapebyte.featureToggles.data.contract.FeatureToggleState
import de.stefan.lang.shapebyte.featureTogglesDomain.contract.LoadFeatureToggleUseCase
import de.stefan.lang.shapebyte.features.workout.data.contract.history.WorkoutHistoryRepository
import de.stefan.lang.shapebyte.features.workout.data.contract.schedule.WorkoutScheduleEntry
import de.stefan.lang.shapebyte.features.workout.domain.contract.workout.history.FetchRecentWorkoutHistoryUseCase
import de.stefan.lang.shapebyte.features.workout.domain.contract.workout.history.HistoryError
import de.stefan.lang.shapebyte.features.workout.domain.implementation.workout.history.FetchRecentWorkoutHistoryUseCaseImpl
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

internal class FetchRecentWorkoutHistoryUseCaseTest : BaseTest() {

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
                    date = Instant.Companion.parse("2022-01-01T00:00:00Z"),
                    progress = Progress.Companion.COMPLETE
                )
            )

            LoadState.Success(list)
        }

        val retVal = FetchRecentWorkoutHistoryUseCaseImpl(
            repository = repository,
            logger = get(),
            coroutineContextProviding = get(),
            coroutineScopeProviding = get(),
            loadFeatureToggleUseCase = loadFeatureToggleUseCase,
        )
        return retVal
    }
}