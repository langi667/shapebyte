package de.stefan.lang.shapebyte.features.workout.api.timed

import de.stefan.lang.coreutils.contract.logging.Logging
import de.stefan.lang.coroutines.contract.CoroutineContextProviding
import de.stefan.lang.foundation.presentation.contract.viewmodel.BaseViewModel
import de.stefan.lang.shapebyte.features.workout.api.Workout

abstract class TimedWorkoutViewModel(
    logger: Logging,
    coroutineContextProvider: CoroutineContextProviding,
) : BaseViewModel(logger, coroutineContextProvider) {

    enum class LaunchState {
        Idle,
        Running,
        Pause,
        Finished,
        ;

        // TODO: Test
        val isRunning: Boolean get() = this == Running
    }

    abstract val workout: Workout?
    abstract val launchState: LaunchState
    abstract val isRunning: Boolean

    abstract fun intent(intent: TimedWorkoutUIIntent)
}
