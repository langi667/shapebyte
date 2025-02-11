package de.stefan.lang.shapebyte.initializing

import de.stefan.lang.foundationCore.platform.PlatformDependencyProvider
import de.stefan.lang.shapebyte.SharedModule
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

abstract class BaseInitializationUseCase {
    val flow: Flow<SharedInitializationState> get() = stateFlow
    protected val stateFlow = MutableStateFlow(SharedInitializationState.UNINITIALIZED)

    val state: SharedInitializationState get() = stateFlow.value
    val isInitialized: Boolean get() = state == SharedInitializationState.INITIALIZED

    /**
     * Performs the app initialization by setting up the dependency graph and other components
     *
     * @param platformDependencies The platform specific dependencies, such as context on Android,
     * main bundle on iOS or navigation handler ...
     */
    fun invoke(platformDependencies: PlatformDependencyProvider): Flow<SharedInitializationState> {
        if (stateFlow.value != SharedInitializationState.UNINITIALIZED) {
            return stateFlow
        }

        stateFlow.value = SharedInitializationState.INITIALIZING
        val dispatcher = platformDependencies.coroutineContextProvider.defaultDispatcher()
        val scope = platformDependencies.coroutineScopeProviding.createCoroutineScope(
            context = SupervisorJob(),
        )
        // needs to be performed directly on the main thread to have the DPI ready
        initializeDependencyGraph(platformDependencies)
        scope.launch(dispatcher) {
            initializeShared(platformDependencies)
        }

        return stateFlow
    }

    /**
     * Use this to initialize dependency injection, only run operations on the main thread here to
     * ensure dependency injection graph is ready before initializeApplication is called
     *
     * @param platformDependencies The platform specific dependencies, such as context on Android,
     * main bundle on iOS or navigation handler ...
     */
    private fun initializeDependencyGraph(
        platformDependencies: PlatformDependencyProvider,
    ) {
        SharedModule.start(platformDependencies)
    }

    /**
     * Use this method to initialize all application dependencies. The dependency injection graph is
     * ready once you call initializeApplication. Make sure to update stateFlow with INITIALIZED
     * once you're done with all operations
     *
     * @param platformDependencies The platform specific dependencies, such as context on Android,
     * main bundle on iOS or navigation handler ...
     */
    protected abstract suspend fun initializeShared(
        platformDependencies: PlatformDependencyProvider,
    )

    protected fun readDeviceInfos() = SharedModule.deviceInfoProvider().readDeviceInfos()
}
