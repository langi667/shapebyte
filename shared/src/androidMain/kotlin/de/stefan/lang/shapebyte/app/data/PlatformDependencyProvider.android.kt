package de.stefan.lang.shapebyte.app.data

import android.content.Context
import de.stefan.lang.shapebyte.utils.app.appcontext.AppContextProvider
import de.stefan.lang.shapebyte.utils.app.appinfo.AppInfo
import de.stefan.lang.shapebyte.utils.app.appresources.AppResourceProvider
import de.stefan.lang.shapebyte.utils.coroutines.CoroutineContextProviding
import de.stefan.lang.shapebyte.utils.coroutines.CoroutineScopeProviding
import de.stefan.lang.shapebyte.utils.resources.audio.AudioMapping

actual data class PlatformDependencyProvider(
    val applicationContext: Context,
    val audioMapping: AudioMapping,
    actual override val coroutineScopeProviding: CoroutineScopeProviding,
    actual override val coroutineContextProvider: CoroutineContextProviding,
    actual override val appInfo: AppInfo,
    actual override val appContextProvider: AppContextProvider,
    actual override val appResourceProvider: AppResourceProvider,
) : PlatformDependencyProviding
