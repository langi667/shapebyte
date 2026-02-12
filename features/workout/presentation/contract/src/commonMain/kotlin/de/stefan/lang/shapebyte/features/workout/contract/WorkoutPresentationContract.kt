package de.stefan.lang.shapebyte.features.workout.contract

import de.stefan.lang.shapebyte.features.navigation.contract.NavigationRequestHandler
import de.stefan.lang.shapebyte.features.workout.contract.preview.WorkoutHistoryPreviewDataProvider
import de.stefan.lang.shapebyte.features.workout.contract.timed.TimedWorkoutViewModel

public interface WorkoutPresentationContract {
    public fun timedWorkoutViewModel(navigationHandler: NavigationRequestHandler): TimedWorkoutViewModel

    public fun workoutHistoryPreviewDataProvider(): WorkoutHistoryPreviewDataProvider
}
