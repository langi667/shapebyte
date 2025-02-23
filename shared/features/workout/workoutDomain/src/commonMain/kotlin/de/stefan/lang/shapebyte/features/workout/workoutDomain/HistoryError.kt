package de.stefan.lang.shapebyte.features.workout.workoutDomain

sealed class HistoryError : Throwable() {
    data object FeatureDisabled : HistoryError()
}
