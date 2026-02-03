package de.stefan.lang.shapebyte.features.workout.contract.timed

import de.stefan.lang.foundation.presentation.contract.intent.UIIntent

public sealed class TimedWorkoutUIIntent : UIIntent {
    public data class Load(val workoutId: Int) : TimedWorkoutUIIntent()
    public data object Start : TimedWorkoutUIIntent()
    public data object PauseOrStartWorkout : TimedWorkoutUIIntent()

    public data object Stop : TimedWorkoutUIIntent()
    public data object OnCloseClicked : TimedWorkoutUIIntent()
}
