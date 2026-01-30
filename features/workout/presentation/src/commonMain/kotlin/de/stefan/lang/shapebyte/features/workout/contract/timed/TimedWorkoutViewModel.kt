package de.stefan.lang.shapebyte.features.workout.contract.timed

import de.stefan.lang.coroutines.contract.CoroutineContextProviding
import de.stefan.lang.foundation.presentation.contract.viewmodel.BaseViewModel
import de.stefan.lang.shapebyte.features.workout.data.contract.Workout
import de.stefan.lang.utils.logging.contract.Logging

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
