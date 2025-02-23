package de.stefan.lang.shapebyte.features.workout.history.domain

sealed class HistoryError : Throwable() {
    data object FeatureDisabled : HistoryError()
}
