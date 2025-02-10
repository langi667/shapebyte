package de.stefan.lang.shapebyte.features.workout.quick.data.mocks

import de.stefan.lang.foundationCore.image.ImageResource
import de.stefan.lang.shapebyte.features.workout.core.data.Workout
import de.stefan.lang.shapebyte.features.workout.core.data.WorkoutType
import de.stefan.lang.shapebyte.features.workout.quick.data.QuickWorkoutsDatasource
import de.stefan.lang.shapebyte.features.workout.quick.domain.QuickWorkoutsError
import de.stefan.lang.shapebyte.shared.loading.data.LoadState

@Suppress("MagicNumber")
class QuickWorkoutsDatasourceMocks : QuickWorkoutsDatasource {
    val workouts = listOf(
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

    override suspend fun fetchQuickWorkouts(): LoadState.Result<List<Workout>> {
        return LoadState.Success(workouts)
    }

    override suspend fun workoutForId(id: Int): LoadState.Result<Workout> {
        val workout = workouts.firstOrNull { it.id == id }
        val result = if (workout != null) {
            LoadState.Success(workout)
        } else {
            LoadState.Error(QuickWorkoutsError.WorkoutDoesNotExist(id))
        }

        return result
    }
}
