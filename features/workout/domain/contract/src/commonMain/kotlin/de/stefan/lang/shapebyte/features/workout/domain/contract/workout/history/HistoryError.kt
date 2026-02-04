package de.stefan.lang.shapebyte.features.workout.domain.contract.workout.history

public sealed class HistoryError : Throwable() {
    public data object FeatureDisabled : HistoryError()
}
