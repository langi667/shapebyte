package de.stefan.lang.shapebyte.features.workout.contract.preview

import de.stefan.lang.foundation.core.contract.image.ImageResource
import de.stefan.lang.shapebyte.features.workout.data.contract.Workout
import de.stefan.lang.shapebyte.features.workout.data.contract.WorkoutType
import de.stefan.lang.shapebyte.features.workout.data.fixture.QuickWorkoutsDatasourceFixture

// TODO: consider using mock data from file
object QuickWorkoutsPreviewDataProvider {
    private val datasource = QuickWorkoutsDatasourceFixture()

    val previewData = datasource.workouts

    val hiit = Workout(
        name = "HIIT Workout",
        shortDescription = "20 min. legs, core",
        image = ImageResource("Sprints.png"),
        id = 1,
        type = WorkoutType.Timed.Interval(0, 0, 0),
    )

    val hiitLongTitle = Workout(
        name = "very long HIIT Workout title that is too long",
        shortDescription = "20 min. legs, core",
        image = ImageResource("Sprints.png"),
        id = 2,
        type = WorkoutType.Timed.Interval(0, 0, 0),
    )

    val hiitLongDescription = Workout(
        name = "HIIT Workout",
        shortDescription = "20 min. legs, core 20 min. legs, core 20 min. legs, core",
        image = ImageResource("Sprints.png"),
        id = 3,
        type = WorkoutType.Timed.Interval(0, 0, 0),
    )

    val hiitLongTitleAndDesc = Workout(
        name = "very long HIIT Workout title that is too long",
        shortDescription = "20 min. legs, core 20 min. legs, core 20 min. legs, core 20 min. " +
            "legs, core",
        image = ImageResource("Sprints.png"),
        id = 4,
        type = WorkoutType.Timed.Interval(0, 0, 0),
    )
}
