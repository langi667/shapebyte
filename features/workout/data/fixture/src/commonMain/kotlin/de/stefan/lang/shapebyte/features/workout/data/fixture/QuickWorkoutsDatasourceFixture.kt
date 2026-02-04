package de.stefan.lang.shapebyte.features.workout.data.fixture

import de.stefan.lang.foundation.core.contract.image.ImageResource
import de.stefan.lang.foundation.core.contract.loadstate.LoadState
import de.stefan.lang.shapebyte.features.workout.data.contract.Workout
import de.stefan.lang.shapebyte.features.workout.data.contract.WorkoutType
import de.stefan.lang.shapebyte.features.workout.data.contract.quick.QuickWorkoutsDatasource
import de.stefan.lang.shapebyte.features.workout.data.contract.quick.QuickWorkoutsError

@Suppress("MagicNumber")
public class QuickWorkoutsDatasourceFixture : QuickWorkoutsDatasource {
    public val workouts: List<Workout> = listOf(
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

    public override suspend fun fetchQuickWorkouts(): LoadState.Result<List<Workout>> {
        return LoadState.Success(workouts)
    }

    public override suspend fun workoutForId(id: Int): LoadState.Result<Workout> {
        val workout = workouts.firstOrNull { it.id == id }
        val result = if (workout != null) {
            LoadState.Success(workout)
        } else {
            LoadState.Error(QuickWorkoutsError.WorkoutDoesNotExist(id))
        }

        return result
    }
}
