package de.stefan.lang.shapebyte.features.workout.quick.domain

sealed class QuickWorkoutsError : Throwable() {
    data object FeatureDisabled : QuickWorkoutsError()
}
