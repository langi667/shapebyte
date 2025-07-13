package de.stefan.lang.shapebyte.features.workout.workoutDomain.workout

sealed class HistoryError : Throwable() {
    data object FeatureDisabled : HistoryError()
}
