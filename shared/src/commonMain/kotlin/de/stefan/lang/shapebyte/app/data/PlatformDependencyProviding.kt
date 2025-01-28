package de.stefan.lang.shapebyte.app.data

import de.stefan.lang.shapebyte.utils.app.appcontext.AppContextProvider
import de.stefan.lang.shapebyte.utils.app.appinfo.AppInfo
import de.stefan.lang.shapebyte.utils.app.appresources.AppResourceProvider
import de.stefan.lang.shapebyte.utils.coroutines.CoroutineContextProviding
import de.stefan.lang.shapebyte.utils.coroutines.CoroutineScopeProviding

interface PlatformDependencyProviding {
    val coroutineContextProvider: CoroutineContextProviding
    val coroutineScopeProviding: CoroutineScopeProviding
    val appInfo: AppInfo
    val appContextProvider: AppContextProvider
    val appResourceProvider: AppResourceProvider
}
