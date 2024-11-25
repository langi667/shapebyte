package de.stefan.lang.shapebyte.app

import de.stefan.lang.shapebyte.di.DPI
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

object AppInitializer {
    enum class State {
        UNINITIALIZED,
        INITIALIZING,
        INITIALIZED,
    }

    private val _state: MutableStateFlow<State> = MutableStateFlow(State.UNINITIALIZED)
    val state: StateFlow<State> = _state

    fun initialize(): Flow<State> {
        if (state.value != State.UNINITIALIZED) {
            return state
        }

        _state.value = State.INITIALIZING

        val scope = CoroutineScope(Dispatchers.Main + SupervisorJob())
        scope.launch {
            val deviceInfoProvider = DPI.deviceInfoProvider()
            // TODO: maybe more async initialization, in case combine and emit
            deviceInfoProvider.readDeviceInfos().collect {
                _state.value = State.INITIALIZED
            }
        }

        return state
    }
}
