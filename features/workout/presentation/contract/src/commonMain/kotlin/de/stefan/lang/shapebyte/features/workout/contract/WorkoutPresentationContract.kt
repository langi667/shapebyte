package de.stefan.lang.shapebyte.features.workout.contract

import de.stefan.lang.shapebyte.features.navigation.contract.NavigationRequestHandling
import de.stefan.lang.shapebyte.features.workout.contract.countdown.CountdownItemSetsViewModel
import de.stefan.lang.shapebyte.features.workout.contract.timed.TimedWorkoutViewModel

public interface WorkoutPresentationContract {
    public fun countdownItemSetsViewModel(): CountdownItemSetsViewModel
    public fun timedWorkoutViewModel(navHandler: NavigationRequestHandling): TimedWorkoutViewModel
}
