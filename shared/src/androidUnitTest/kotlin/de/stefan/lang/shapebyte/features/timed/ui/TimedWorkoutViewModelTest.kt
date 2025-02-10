package de.stefan.lang.shapebyte.features.timed.ui

import app.cash.turbine.test
import de.stefan.lang.foundationCore.stringformatter.DateTimeStringFormatter
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
    fun `load should load workout data`() = test {
        featureDatasource.addFeatureToggle(
            FeatureToggle(
                FeatureId.QUICK_WORKOUTS.name,
                FeatureToggleState.ENABLED,
            ),
        )

        val sut = createSUT()
        val workoutId = 1
        val workout = datasource.workouts.firstOrNull { it.id == workoutId }
        sut.load(1)

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

        sut.load(workout.id)

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
            assertTrue(dataState.data.playButtonVisible)

            assertEquals(TimedWorkoutViewModel.LaunchState.Idle, dataState.data.launchState)

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

                val setDuration = if (item.data.exercise == null) {
                    0
                } else {
                    1000
                }

                assertEquals(setDuration, item.data.setDuration)
                assertEquals(started, item.data.stopButtonVisible)
                assertEquals(started, item.data.pauseButtonVisible)
            } while (sut.isRunning)

            val finishedState = awaitItem() as UIState.Data<*>

            assertIs<UIState.Data<TimedWorkoutViewData>>(finishedState)
            assertFalse(finishedState.data.pauseButtonVisible)
            assertFalse(finishedState.data.stopButtonVisible)
            assertTrue(finishedState.data.playButtonVisible)

            assertFalse(sut.isRunning)
            assertEquals("00:00", finishedState.data.elapsedTotal)
            assertEquals("00:00", finishedState.data.remainingTotal)
            assertEquals(TimedWorkoutViewModel.LaunchState.Finished, finishedState.data.launchState)

            for (i in 0 until workoutType.secondsTotal) {
                val currElapsed = dateTimeStringFormatter.formatSecondsToString(i)
                val currRemaining =
                    dateTimeStringFormatter.formatSecondsToString(workoutType.secondsTotal - i)

                assertEquals(currElapsed, elapsed.distinct()[i])
                assertEquals(currRemaining, remaining.distinct()[i])
            }
        }
    }

    @Test
    fun `pause must pause correctly`() = test {
        featureDatasource.addFeatureToggle(
            FeatureToggle(
                FeatureId.QUICK_WORKOUTS.name,
                FeatureToggleState.ENABLED,
            ),
        )

        val sut = createSUT()
        val workout = datasource.workouts.first { it.type is WorkoutType.Timed.Interval }
        val workoutType = workout.type as WorkoutType.Timed.Interval

        sut.load(workout.id)

        val elapsed = mutableListOf<String>()
        val remaining = mutableListOf<String>()

        // Initial/ Update state. Wait until data is updated
        sut.state.test {
            assertEquals(UIState.Loading, awaitItem())
            val dataState = awaitItem() as UIState.Data<*>

            assertIs<UIState.Data<TimedWorkoutViewData>>(dataState)
            expectNoEvents()
        }

        sut.start()
        assertTrue(sut.isRunning)

        sut.state.test {
            var isRunning = true

            do {
                val item = awaitItem()
                if (item is UIState.Data<*>) {
                    val data = item.data as TimedWorkoutViewData

                    elapsed.add(data.elapsedTotal)
                    remaining.add(data.remainingTotal)

                    if (data.progressTotal >= 0.5f) {
                        sut.pauseOrStartWorkout()
                        isRunning = false
                    }
                }
            } while (isRunning)

            // TODO: check why still is Running here
            awaitItem()
            val item = awaitItem()
            assertEquals(TimedWorkoutViewModel.LaunchState.Pause, sut.launchState)
            assertEquals(TimedWorkoutViewModel.LaunchState.Pause, item.dataOrNull<TimedWorkoutViewData>()?.launchState)

            expectNoEvents()
        }

        // resuming
        sut.pauseOrStartWorkout()

        sut.state.test {
            awaitItem()
            var isRunning = true
            do {
                val item = awaitItem()

                assertIs<UIState.Data<TimedWorkoutViewData>>(item)
                if (item.data.launchState == TimedWorkoutViewModel.LaunchState.Running) {
                    elapsed.add(item.data.elapsedTotal)
                    remaining.add(item.data.remainingTotal)
                }
                else if (item.data.launchState == TimedWorkoutViewModel.LaunchState.Finished) {
                    isRunning = false
                }

            } while (isRunning)

            expectNoEvents()
        }

        for (i in 0 until workoutType.secondsTotal) {
            val currElapsed = dateTimeStringFormatter.formatSecondsToString(i)
            val currRemaining =
                dateTimeStringFormatter.formatSecondsToString(workoutType.secondsTotal - i)

            assertEquals(currElapsed, elapsed.distinct()[i])
            assertEquals(currRemaining, remaining.distinct()[i])
        }
    }

    @Test
    fun `stop must stop correctly`() = test {
        featureDatasource.addFeatureToggle(
            FeatureToggle(
                FeatureId.QUICK_WORKOUTS.name,
                FeatureToggleState.ENABLED,
            ),
        )

        val sut = createSUT()
        val workout = datasource.workouts.first { it.type is WorkoutType.Timed.Interval }

        sut.load(workout.id)

        val elapsed = mutableListOf<String>()
        val remaining = mutableListOf<String>()

        // Initial/ Update state. Wait until data is updated
        sut.state.test {
            assertEquals(UIState.Loading, awaitItem())
            val dataState = awaitItem() as UIState.Data<*>

            assertIs<UIState.Data<TimedWorkoutViewData>>(dataState)
            expectNoEvents()
        }

        sut.start()
        assertTrue(sut.isRunning)

        sut.state.test {
            var isRunning = true

            do {
                val item = awaitItem()
                if (item is UIState.Data<*>) {
                    val data = item.data as TimedWorkoutViewData

                    elapsed.add(data.elapsedTotal)
                    remaining.add(data.remainingTotal)

                    if (data.progressTotal >= 0.5f) {
                        sut.stop()
                        isRunning = false
                    }
                }
            } while (isRunning)

            // TODO: check why still is Running here
            awaitItem()
            val item = awaitItem()
            assertEquals(TimedWorkoutViewModel.LaunchState.Finished, sut.launchState)
            assertEquals(TimedWorkoutViewModel.LaunchState.Finished, item.dataOrNull<TimedWorkoutViewData>()?.launchState)

            expectNoEvents()
        }
    }
    // TODO: test error state if implemented

    private fun createSUT(): TimedWorkoutViewModel {
        return DPI.timedWorkoutViewModel()
    }
}
