package de.stefan.lang.shapebyte.initializing

import de.stefan.lang.coreCoroutinesProviding.CoroutineContextProviding
import de.stefan.lang.coreCoroutinesProviding.CoroutineScopeProviding
import de.stefan.lang.coreutils.nativecontext.ContextProvider
import de.stefan.lang.foundationCore.app.AppInfo
import de.stefan.lang.foundationCore.resources.impl.AppResourceProvider
// TODO: consider moving back to shared
expect class PlatformDependencyProvider : PlatformDependencyProviding {
    override val coroutineContextProvider: CoroutineContextProviding
    override val coroutineScopeProviding: CoroutineScopeProviding
    override val appInfo: AppInfo
    override val appContextProvider: ContextProvider
    override val appResourceProvider: AppResourceProvider
}
