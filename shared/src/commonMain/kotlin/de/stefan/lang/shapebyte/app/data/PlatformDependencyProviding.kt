package de.stefan.lang.shapebyte.app.data

import de.stefan.lang.core.app.AppContextProvider
import de.stefan.lang.core.coroutines.CoroutineContextProviding
import de.stefan.lang.core.coroutines.CoroutineScopeProviding
import de.stefan.lang.core.resources.impl.AppResourceProvider
import de.stefan.lang.core.app.AppInfo

interface PlatformDependencyProviding {
    val coroutineContextProvider: CoroutineContextProviding
    val coroutineScopeProviding: CoroutineScopeProviding
    val appInfo: AppInfo
    val appContextProvider: AppContextProvider
    val appResourceProvider: AppResourceProvider
}
