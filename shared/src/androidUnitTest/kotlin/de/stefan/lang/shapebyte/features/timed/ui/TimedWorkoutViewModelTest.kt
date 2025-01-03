package de.stefan.lang.shapebyte.features.timed.ui

import app.cash.turbine.test
import de.stefan.lang.shapebyte.di.DPI
import de.stefan.lang.shapebyte.features.core.domain.FeatureId
import de.stefan.lang.shapebyte.features.workout.core.data.WorkoutType
import de.stefan.lang.shapebyte.features.workout.item.timed.ui.TimedWorkoutViewData
import de.stefan.lang.shapebyte.features.workout.item.timed.ui.TimedWorkoutViewModel
import de.stefan.lang.shapebyte.features.workout.quick.data.mocks.QuickWorkoutsDatasourceMocks
import de.stefan.lang.shapebyte.shared.featuretoggles.data.FeatureToggle
import de.stefan.lang.shapebyte.shared.featuretoggles.data.FeatureToggleState
import de.stefan.lang.shapebyte.shared.featuretoggles.data.impl.FeatureToggleDatasourceMock
import de.stefan.lang.shapebyte.shared.featuretoggles.di.featureToggleDatasourceMock
import de.stefan.lang.shapebyte.shared.featuretoggles.di.quickWorkoutsDatasourceMock
import de.stefan.lang.shapebyte.shared.viewmodel.ui.UIState
import de.stefan.lang.shapebyte.utils.BaseCoroutineTest
import de.stefan.lang.shapebyte.utils.datetime.DateTimeStringFormatter
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertIs
import kotlin.test.assertNull
import kotlin.test.assertTrue

class TimedWorkoutViewModelTest : BaseCoroutineTest() {
    private val datasource: QuickWorkoutsDatasourceMocks
        get() = DPI.quickWorkoutsDatasourceMock

    private val featureDatasource: FeatureToggleDatasourceMock
        get() = DPI.featureToggleDatasourceMock

    private val dateTimeStringFormatter: DateTimeStringFormatter
        get() = DPI.dateTimeStringFormatter()

    @Test
    fun `test initial state`() = test {
        val sut = createSUT()
        assertEquals(UIState.Idle, sut.state.value)
        assertNull(sut.workout)
        assertFalse(sut.isRunning)
    }

    @Test
    fun `update should update data`() = test {
        featureDatasource.addFeatureToggle(
            FeatureToggle(
                FeatureId.QUICK_WORKOUTS.name,
                FeatureToggleState.ENABLED,
            ),
        )

        val sut = createSUT()
        val workoutId = 1
        val workout = datasource.workouts.firstOrNull { it.id == workoutId }
        sut.update(1)

        sut.state.test {
            assertEquals(UIState.Loading, awaitItem())
            val dataState = awaitItem() as UIState.Data<*>

            assertIs<UIState.Data<TimedWorkoutViewData>>(dataState)
            assertEquals(workout, sut.workout)
            assertFalse(sut.isRunning)

            expectNoEvents()
        }
    }

    @Test
    fun `start should do nothing if update was not called`() = test {
        val sut = createSUT()
        sut.start()

        sut.state.test {
            assertEquals(UIState.Idle, awaitItem())
            expectNoEvents()
        }
    }

    @Test
    fun `start should run the workout till finished`() = test {
        featureDatasource.addFeatureToggle(
            FeatureToggle(
                FeatureId.QUICK_WORKOUTS.name,
                FeatureToggleState.ENABLED,
            ),
        )

        val sut = createSUT()

        val workout = datasource.workouts.first { it.type is WorkoutType.Timed.Interval }
        val workoutType = workout.type as WorkoutType.Timed.Interval

        sut.update(workout.id)

        // Initial/ Update state
        sut.state.test {
            assertEquals(UIState.Loading, awaitItem())
            val dataState = awaitItem() as UIState.Data<*>

            assertIs<UIState.Data<TimedWorkoutViewData>>(dataState)

            // Display state before play button is clicked
            assertEquals(workout.name, dataState.data.title)
            assertEquals(
                dateTimeStringFormatter.formatSecondsToString(0),
                dataState.data.elapsedTotal,
            )
            assertEquals(
                dateTimeStringFormatter.formatSecondsToString(workoutType.secondsTotal),
                dataState.data.remainingTotal,
            )
            assertEquals(0f, dataState.data.progressTotal)
            assertFalse(dataState.data.pauseButtonVisible)
            assertFalse(dataState.data.stopButtonVisible)

            assertEquals(workout, sut.workout)
            assertFalse(sut.isRunning)

            expectNoEvents()
        }

        sut.start()
        assertTrue(sut.isRunning)

        sut.state.test {
            val elapsed = mutableListOf<String>()
            val remaining = mutableListOf<String>()

            do {
                val item = awaitItem()
                assertIs<UIState.Data<TimedWorkoutViewData>>(item)

                assertEquals(workout.name, item.data.title)
                val started = item.data.launchState.isRunning

                if (sut.isRunning) {
                    elapsed.add(item.data.elapsedTotal)
                    remaining.add(item.data.remainingTotal)
                }

                val setDuration = if (started) {
                    1000
                } else {
                    0
                }

                assertEquals(setDuration, item.data.setDuration)
                assertEquals(started, item.data.stopButtonVisible)
                assertEquals(started, item.data.pauseButtonVisible)
            } while (sut.isRunning)

            for (i in 0..workoutType.secondsTotal) {
                val currElapsed = dateTimeStringFormatter.formatSecondsToString(i)
                val currRemaining =
                    dateTimeStringFormatter.formatSecondsToString(workoutType.secondsTotal - i)

                assertEquals(currElapsed, elapsed.distinct()[i])
                assertEquals(currRemaining, remaining.distinct()[i])
            }
        }
    }

    // TODO: test error state if implemented

    private fun createSUT(): TimedWorkoutViewModel {
        return DPI.timedWorkoutViewModel()
    }
}
