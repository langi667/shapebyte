package de.stefan.lang.shapebyte.featureCore.platformdependencies

import de.stefan.lang.foundationCore.app.AppInfo
import de.stefan.lang.coreutils.nativecontext.ContextProvider
import de.stefan.lang.coroutines.api.CoroutineContextProviding
import de.stefan.lang.coroutines.api.CoroutineScopeProviding
import de.stefan.lang.foundationCore.resources.impl.AppResourceProvider
import platform.Foundation.NSBundle

actual data class PlatformDependencyProvider(
    val bundle: NSBundle,
    actual override val appInfo: AppInfo,
    actual override val appContextProvider: ContextProvider,
    actual override val appResourceProvider: AppResourceProvider,
) : PlatformDependencyProviding