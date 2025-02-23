package de.stefan.lang.shapebyte.features.workout.workoutData

sealed class QuickWorkoutsError : Throwable() {
    data object FeatureDisabled : QuickWorkoutsError()
    data class WorkoutDoesNotExist(val workoutId: Int) : QuickWorkoutsError()
}
