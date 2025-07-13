package de.stefan.lang.shapebyte.features.workout.timed

import app.cash.turbine.test
import de.stefan.lang.featureToggles.api.FeatureToggle
import de.stefan.lang.featureToggles.api.LoadFeatureToggleUseCase
import de.stefan.lang.featureToggles.api.FeatureToggleState
import de.stefan.lang.foundationCore.api.image.ImageResource
import de.stefan.lang.foundationCore.api.loadstate.LoadState
import de.stefan.lang.foundationCore.api.stringformatter.DateTimeStringFormatter
import de.stefan.lang.foundationPresentation.api.state.UIState
import de.stefan.lang.featureToggles.api.FeatureId
import de.stefan.lang.shapebyte.features.workout.WorkoutFeatureTest
import de.stefan.lang.shapebyte.features.workout.api.Workout
import de.stefan.lang.shapebyte.features.workout.api.timed.TimedWorkoutViewData
import de.stefan.lang.shapebyte.features.workout.api.WorkoutType
import de.stefan.lang.shapebyte.features.workout.api.quick.QuickWorkoutsError
import de.stefan.lang.shapebyte.features.workout.api.timed.TimedWorkoutUIIntent
import de.stefan.lang.shapebyte.features.workout.api.timed.TimedWorkoutViewModel
import de.stefan.lang.shapebyte.features.workout.presentation.timed.TimedWorkoutViewModelImpl
import de.stefan.lang.shapebyte.features.workout.workoutData.workout.QuickWorkoutsRepository
import de.stefan.lang.shapebyte.features.workout.workoutDomain.workout.QuickWorkoutForIdUseCaseImpl
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import org.koin.core.component.get
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertIs
import kotlin.test.assertNull
import kotlin.test.assertTrue

class TimedWorkoutViewModelTest : WorkoutFeatureTest() {
    private val dateTimeStringFormatter: DateTimeStringFormatter by lazy {
        get()
    }

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

    private val repository: QuickWorkoutsRepository = mockk(relaxed = true) {
        coEvery { fetchQuickWorkouts() } returns LoadState.Success(workouts)
        coEvery { workoutForId(any()) } coAnswers {
            val workoutIdArg = invocation.args[0] as Int
            val workout = workouts.firstOrNull { it.id == workoutIdArg }

            if (workout == null) {
                LoadState.Error(QuickWorkoutsError.WorkoutDoesNotExist(workoutIdArg))
            } else {
                LoadState.Success(workout)
            }
        }
    }

    private val loadFeatureToggleUseCase: LoadFeatureToggleUseCase = mockk(relaxed = true)

    @Test
    fun `test initial state`() = test {
        val sut = createSUT()
        assertEquals(UIState.Idle, sut.state.value)
        assertNull(sut.workout)
        assertFalse(sut.isRunning)
    }


    @Test
    fun `load should load workout data`() = test {
        val sut = createSUT()
        val workoutId = 1
        val workout = workouts.first { it.id == workoutId }

        sut.intent(TimedWorkoutUIIntent.Load(workoutId))

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
        sut.intent(TimedWorkoutUIIntent.Start)

        sut.state.test {
            assertEquals(UIState.Idle, awaitItem())
            expectNoEvents()
        }
    }


    @Test
    fun `start should run the workout till finished`() = test {
        val sut = createSUT()
        val workout = workouts.first { it.type is WorkoutType.Timed.Interval }
        val workoutType = workout.type as WorkoutType.Timed.Interval

        sut.intent(TimedWorkoutUIIntent.Load(workout.id))

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

        sut.intent(TimedWorkoutUIIntent.Start)
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
        val sut = createSUT()
        val workout = workouts.first { it.type is WorkoutType.Timed.Interval }
        val workoutType = workout.type as WorkoutType.Timed.Interval

        sut.intent(TimedWorkoutUIIntent.Load(workout.id))

        val elapsed = mutableListOf<String>()
        val remaining = mutableListOf<String>()

        // Initial/ Update state. Wait until data is updated
        sut.state.test {
            assertEquals(UIState.Loading, awaitItem())
            val dataState = awaitItem() as UIState.Data<*>

            assertIs<UIState.Data<TimedWorkoutViewData>>(dataState)
            expectNoEvents()
        }

        sut.intent(TimedWorkoutUIIntent.Start)
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
                        sut.intent(TimedWorkoutUIIntent.PauseOrStartWorkout)
                        isRunning = false
                    }
                }
            } while (isRunning)

            val item = awaitItem()
            assertEquals(TimedWorkoutViewModel.LaunchState.Pause, sut.launchState)
            assertEquals(
                TimedWorkoutViewModel.LaunchState.Pause,
                item.dataOrNull<TimedWorkoutViewData>()?.launchState
            )

            expectNoEvents()
        }

        // resuming
        sut.intent(TimedWorkoutUIIntent.PauseOrStartWorkout)

        sut.state.test {
            awaitItem()
            var isRunning = true
            do {
                val item = awaitItem()

                assertIs<UIState.Data<TimedWorkoutViewData>>(item)
                if (item.data.launchState == TimedWorkoutViewModel.LaunchState.Running) {
                    elapsed.add(item.data.elapsedTotal)
                    remaining.add(item.data.remainingTotal)
                } else if (item.data.launchState == TimedWorkoutViewModel.LaunchState.Finished) {
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
        val sut = createSUT()
        val workout = workouts.first { it.type is WorkoutType.Timed.Interval }

        sut.intent(TimedWorkoutUIIntent.Load(workout.id))

        val elapsed = mutableListOf<String>()
        val remaining = mutableListOf<String>()

        // Initial/ Update state. Wait until data is updated
        sut.state.test {
            assertEquals(UIState.Loading, awaitItem())
            val dataState = awaitItem() as UIState.Data<*>

            assertIs<UIState.Data<TimedWorkoutViewData>>(dataState)
            expectNoEvents()
        }

        sut.intent(TimedWorkoutUIIntent.Start)
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
                        sut.intent(TimedWorkoutUIIntent.Stop)
                        isRunning = false
                    }
                }
            } while (isRunning)

            val item = awaitItem()
            assertEquals(TimedWorkoutViewModel.LaunchState.Finished, sut.launchState)
            assertEquals(
                TimedWorkoutViewModel.LaunchState.Finished,
                item.dataOrNull<TimedWorkoutViewData>()?.launchState
            )

            expectNoEvents()
        }
    }
    // TODO: test error state if implemented

    private fun createSUT(
        featureToggleEnabled: Boolean = true
    ): TimedWorkoutViewModel {
        val ffState = if (featureToggleEnabled) {
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

        return TimedWorkoutViewModelImpl(
            navigationHandler = mockk(relaxed = true),
            quickWorkoutForIdUseCase = QuickWorkoutForIdUseCaseImpl(
                repository = repository,
                logger = get(),
                coroutineContextProvider = get(),
                coroutineScopeProvider = get(),
                loadFeatureToggleUseCase = loadFeatureToggleUseCase,
            ),
            itemsExecutionBuilder = get(),
            dateStringFormatter = get(),
            logger = get(),
            audioPlayer = get(),
            coroutineContextProvider = get(),
        )
    }
}
