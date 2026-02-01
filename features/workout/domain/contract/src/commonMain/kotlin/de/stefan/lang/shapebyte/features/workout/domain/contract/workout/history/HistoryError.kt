package de.stefan.lang.shapebyte.features.workout.domain.contract.workout.history

sealed class HistoryError : Throwable() {
    data object FeatureDisabled : HistoryError()
}
