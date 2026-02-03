package de.stefan.lang.shapebyte.features.workout.contract.timed

import de.stefan.lang.coroutines.contract.CoroutineContextProviding
import de.stefan.lang.foundation.presentation.contract.viewmodel.BaseViewModel
import de.stefan.lang.shapebyte.features.workout.data.contract.Workout
import de.stefan.lang.utils.logging.contract.Logging

public abstract class TimedWorkoutViewModel(
    logger: Logging,
    coroutineContextProvider: CoroutineContextProviding,
) : BaseViewModel(logger, coroutineContextProvider) {

    public enum class LaunchState {
        Idle,
        Running,
        Pause,
        Finished,
        ;

        // TODO: Test
        public val isRunning: Boolean get() = this == Running
    }

    public abstract val workout: Workout?
    public abstract val launchState: LaunchState
    public abstract val isRunning: Boolean

    public abstract fun intent(intent: TimedWorkoutUIIntent)
}
