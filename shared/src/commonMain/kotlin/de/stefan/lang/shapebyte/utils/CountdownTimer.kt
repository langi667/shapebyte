package de.stefan.lang.shapebyte.utils

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.coroutineContext
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

class CountdownTimer(
    private val logger: Logging,
) {
    sealed class State {
        sealed class ValuedState : State() {
            abstract val elapsed: Duration
            abstract val duration: Duration

            val progress: Progress
                get() = Progress((elapsed.inWholeSeconds / duration.inWholeSeconds).toFloat())

            fun nextProgress(interval: Duration): Progress {
                val nextElapsed = elapsed + interval
                return Progress((nextElapsed.inWholeSeconds / duration.inWholeSeconds).toFloat())
            }
        }

        data object Idle : State()
        data class Paused(
            override val elapsed: Duration,
            override val duration: Duration,
            val interval: Duration,
        ) : ValuedState()

        data class Running(
            override val elapsed: Duration,
            override val duration: Duration,
            val interval: Duration,
        ) : ValuedState()

        data class Stopped(override val elapsed: Duration, override val duration: Duration) :
            ValuedState()

        // TODO: test
        val isFinished: Boolean
            get() = when (val stoppedState = this as? Stopped) {
                null -> false
                else -> stoppedState.elapsed >= stoppedState.duration
            }

        // TODO: test
        val isCanceled: Boolean
            get() = when (val stoppedState = this as? Stopped) {
                null -> false
                else -> stoppedState.elapsed < stoppedState.duration
            }
    }

    data class Data(
        var duration: Duration = 0.seconds,
        var interval: Duration = 0.seconds,
    )

    private var data: Data? = null
    private val _stateFlow = MutableStateFlow<State>(State.Idle)
    private var currRunJob: Job? = null

    var state: State
        get() = _stateFlow.value
        private set(value) {
            _stateFlow.value = value
        }

    private var elapsed = 0.seconds

    fun launch(
        duration: Duration,
        interval: Duration,
        scope: CoroutineScope,
    ): StateFlow<State> {
        setup(duration, interval)
        return start(scope)
    }

    fun setup(duration: Duration, interval: Duration) {
        if (state is State.Running || state is State.Paused) {
            stop()
        }

        reset()
        this.data = Data(duration, interval)
    }

    fun start(
        scope: CoroutineScope,
    ): StateFlow<State> {
        withData { data ->
            if (state is State.Running) {
                return@withData
            }

            if (state is State.Stopped) {
                reset()
            }

            state = State.Running(elapsed, data.duration, data.interval)
            currRunJob = scope.launch {
                run()
            }
        }

        return _stateFlow
    }

    fun pause() {
        withData { data ->
            if (state is State.Running) {
                state = State.Paused(elapsed, data.duration, data.interval)
            }
        }
    }

    fun stop() {
        withData { data ->
            this.state = State.Stopped(elapsed, data.duration)
            currRunJob?.cancel()

            reset()
        }
    }

    private fun reset() {
        elapsed = 0.seconds
    }

    private suspend fun run() {
        while (canRun(coroutineContext)) {
            coWithData { data ->
                delay(data.interval)

                if (!canRun(coroutineContext)) {
                    return@coWithData
                }

                elapsed += data.interval

                if (elapsed <= data.duration) {
                    _stateFlow.value = State.Running(elapsed, data.duration, data.interval)
                }

                if (elapsed >= data.duration) {
                    stop()
                    return@coWithData
                }
            }
        }
    }

    private fun canRun(context: CoroutineContext) = state is State.Running && context.isActive
    private fun logNoData() = logger.e("CountdownTimer", "No duration or interval set")

    private fun withData(block: (Data) -> Unit) {
        val data = this.data ?: run {
            logNoData()
            return
        }

        block(data)
    }

    private suspend fun coWithData(block: suspend (Data) -> Unit) {
        val data = this.data ?: run {
            logNoData()
            return
        }

        block(data)
    }
}
