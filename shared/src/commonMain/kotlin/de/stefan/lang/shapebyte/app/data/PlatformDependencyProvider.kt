package de.stefan.lang.shapebyte.app.data

import de.stefan.lang.shapebyte.utils.app.appinfo.AppInfo
import de.stefan.lang.shapebyte.utils.coroutines.CoroutineContextProviding
import de.stefan.lang.shapebyte.utils.coroutines.CoroutineScopeProviding

expect class PlatformDependencyProvider : PlatformDependencyProviding {
    override val coroutineContextProvider: CoroutineContextProviding
    override val coroutineScopeProviding: CoroutineScopeProviding
    override val appInfo: AppInfo
}
