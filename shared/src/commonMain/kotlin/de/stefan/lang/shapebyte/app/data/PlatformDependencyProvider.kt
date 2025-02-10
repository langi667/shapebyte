package de.stefan.lang.shapebyte.app.data

import de.stefan.lang.coreutils.coroutines.CoroutineContextProviding
import de.stefan.lang.coreutils.coroutines.CoroutineScopeProviding
import de.stefan.lang.coreutils.nativecontext.ContextProvider
import de.stefan.lang.foundationCore.app.AppInfo
import de.stefan.lang.foundationCore.resources.impl.AppResourceProvider

expect class PlatformDependencyProvider : PlatformDependencyProviding {
    override val coroutineContextProvider: CoroutineContextProviding
    override val coroutineScopeProviding: CoroutineScopeProviding
    override val appInfo: AppInfo
    override val appContextProvider: ContextProvider
    override val appResourceProvider: AppResourceProvider
}
