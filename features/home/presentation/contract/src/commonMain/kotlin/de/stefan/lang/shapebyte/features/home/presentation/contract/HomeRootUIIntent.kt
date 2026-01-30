package de.stefan.lang.shapebyte.features.home.presentation.contract

import de.stefan.lang.foundation.presentation.contract.intent.UIIntent
import de.stefan.lang.shapebyte.features.workout.data.contract.Workout

public sealed class HomeRootUIIntent : UIIntent {
    public data object Update : HomeRootUIIntent()
    public data class QuickWorkoutSelected(val workout: Workout) : HomeRootUIIntent()
}
