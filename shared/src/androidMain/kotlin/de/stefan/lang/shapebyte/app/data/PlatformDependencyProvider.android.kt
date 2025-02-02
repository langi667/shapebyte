package de.stefan.lang.shapebyte.app.data

import android.content.Context
import de.stefan.lang.core.app.AppContextProvider
import de.stefan.lang.core.coroutines.CoroutineContextProviding
import de.stefan.lang.core.coroutines.CoroutineScopeProviding
import de.stefan.lang.core.resources.impl.AppResourceProvider
import de.stefan.lang.core.app.AppInfo

actual data class PlatformDependencyProvider(
    val applicationContext: Context,
    actual override val coroutineScopeProviding: CoroutineScopeProviding,
    actual override val coroutineContextProvider: CoroutineContextProviding,
    actual override val appInfo: AppInfo,
    actual override val appContextProvider: AppContextProvider,
    actual override val appResourceProvider: AppResourceProvider,
) : PlatformDependencyProviding
