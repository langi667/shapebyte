package de.stefan.lang.shapebyte.initializing

import de.stefan.lang.core.CoreModule
import de.stefan.lang.foundationCore.api.platformdependencies.PlatformDependencyProviding
import de.stefan.lang.shapebyte.SharedModule
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import org.koin.core.module.Module

abstract class BaseInitializationUseCase {
    val flow: Flow<AppInitializationState> get() = stateFlow
    protected val stateFlow = MutableStateFlow(AppInitializationState.UNINITIALIZED)

    val state: AppInitializationState get() = stateFlow.value
    val isInitialized: Boolean get() = state == AppInitializationState.INITIALIZED

    /**
     * Performs the app initialization by setting up the dependency graph and other components
     *
     * @param platformDependencies The platform specific dependencies, such as context on Android,
     * main bundle on iOS or navigation handler ...
     */
    fun invoke(
        platformDependencies: PlatformDependencyProviding,
        modules: List<Module>,
    ): Flow<AppInitializationState> {
        if (stateFlow.value != AppInitializationState.UNINITIALIZED) {
            return stateFlow
        }

        stateFlow.value = AppInitializationState.INITIALIZING
        // needs to be performed directly on the main thread to have the DPI ready
        initializeDependencyGraph(platformDependencies, modules)

        val dispatcher = CoreModule.coroutineContextProvider().defaultDispatcher()
        val scope = CoreModule.coroutineScopeProvider().createCoroutineScope(
            context = SupervisorJob(),
        )

        scope.launch(dispatcher) {
            initializeShared(platformDependencies, modules = modules)
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
    protected abstract fun initializeDependencyGraph(
        platformDependencies: PlatformDependencyProviding,
        modules: List<Module>,
    )

    /**
     * Use this method to initialize all application dependencies. The dependency injection graph is
     * ready once you call initializeApplication. Make sure to update stateFlow with INITIALIZED
     * once you're done with all operations
     *
     * @param platformDependencies The platform specific dependencies, such as context on Android,
     * main bundle on iOS or navigation handler ...
     */
    protected abstract suspend fun initializeShared(
        platformDependencies: PlatformDependencyProviding,
        modules: List<Module>,
    )

    // TODO: solve differently, no references to SharedModule
    protected fun readDeviceInfos() = SharedModule.deviceInfoProvider().readDeviceInfos()
}
