package de.stefan.lang.shapebyte.features.workout.quick.ui

import de.stefan.lang.shapebyte.features.workout.core.data.Workout

sealed interface QuickWorkoutsUIState {
    data class Enabled(val data: List<Workout>) : QuickWorkoutsUIState
    data object Hidden : QuickWorkoutsUIState

    val isEnabled: Boolean
        get() = this is Enabled
}
