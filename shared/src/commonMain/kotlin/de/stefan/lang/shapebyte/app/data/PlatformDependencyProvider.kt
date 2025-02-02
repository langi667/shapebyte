package de.stefan.lang.shapebyte.app.data

import de.stefan.lang.core.app.AppContextProvider
import de.stefan.lang.core.coroutines.CoroutineContextProviding
import de.stefan.lang.core.coroutines.CoroutineScopeProviding
import de.stefan.lang.core.resources.impl.AppResourceProvider
import de.stefan.lang.core.app.AppInfo

expect class PlatformDependencyProvider : PlatformDependencyProviding {
    override val coroutineContextProvider: CoroutineContextProviding
    override val coroutineScopeProviding: CoroutineScopeProviding
    override val appInfo: AppInfo
    override val appContextProvider: AppContextProvider
    override val appResourceProvider: AppResourceProvider
}
