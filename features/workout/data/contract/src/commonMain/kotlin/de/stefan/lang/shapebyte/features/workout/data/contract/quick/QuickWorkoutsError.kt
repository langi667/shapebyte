package de.stefan.lang.shapebyte.features.workout.data.contract.quick

public sealed class QuickWorkoutsError : Throwable() {
    public data object FeatureDisabled : QuickWorkoutsError()
    public data class WorkoutDoesNotExist(public val workoutId: Int) : QuickWorkoutsError()
}
