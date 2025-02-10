package de.stefan.lang.shapebyte.app.data

import de.stefan.lang.coreutils.coroutines.CoroutineContextProviding
import de.stefan.lang.coreutils.coroutines.CoroutineScopeProviding
import de.stefan.lang.coreutils.nativecontext.ContextProvider
import de.stefan.lang.foundationCore.app.AppInfo
import de.stefan.lang.foundationCore.resources.impl.AppResourceProvider

interface PlatformDependencyProviding {
    val coroutineContextProvider: CoroutineContextProviding
    val coroutineScopeProviding: CoroutineScopeProviding
    val appInfo: AppInfo
    val appContextProvider: ContextProvider
    val appResourceProvider: AppResourceProvider
}
