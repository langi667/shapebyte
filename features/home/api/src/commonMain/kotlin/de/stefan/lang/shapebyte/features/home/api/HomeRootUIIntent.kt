package de.stefan.lang.shapebyte.features.home.api

import de.stefan.lang.foundationUi.api.intent.UIIntent
import de.stefan.lang.shapebyte.features.workout.api.Workout

sealed class HomeRootUIIntent : UIIntent {
    data object Update : HomeRootUIIntent()
    data class QuickWorkoutSelected(val workout: Workout) : HomeRootUIIntent()
}
