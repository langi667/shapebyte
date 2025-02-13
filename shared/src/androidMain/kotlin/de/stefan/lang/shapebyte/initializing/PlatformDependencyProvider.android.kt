package de.stefan.lang.shapebyte.initializing

import android.content.Context
import de.stefan.lang.coreCoroutinesProviding.CoroutineContextProviding
import de.stefan.lang.coreCoroutinesProviding.CoroutineScopeProviding
import de.stefan.lang.coreutils.nativecontext.ContextProvider
import de.stefan.lang.foundationCore.app.AppInfo
import de.stefan.lang.foundationCore.resources.impl.AppResourceProvider

actual data class PlatformDependencyProvider(
    val applicationContext: Context,
    actual override val coroutineScopeProviding: CoroutineScopeProviding,
    actual override val coroutineContextProvider: CoroutineContextProviding,
    actual override val appInfo: AppInfo,
    actual override val appContextProvider: ContextProvider,
    actual override val appResourceProvider: AppResourceProvider,
) : PlatformDependencyProviding
