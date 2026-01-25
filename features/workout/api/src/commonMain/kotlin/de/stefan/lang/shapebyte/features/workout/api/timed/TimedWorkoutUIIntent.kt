package de.stefan.lang.shapebyte.features.workout.api.timed

import de.stefan.lang.foundation.presentation.contract.intent.UIIntent

sealed class TimedWorkoutUIIntent : UIIntent {
    data class Load(val workoutId: Int) : TimedWorkoutUIIntent()
    data object Start : TimedWorkoutUIIntent()
    data object PauseOrStartWorkout : TimedWorkoutUIIntent()

    data object Stop : TimedWorkoutUIIntent()
    data object OnCloseClicked : TimedWorkoutUIIntent()
}
