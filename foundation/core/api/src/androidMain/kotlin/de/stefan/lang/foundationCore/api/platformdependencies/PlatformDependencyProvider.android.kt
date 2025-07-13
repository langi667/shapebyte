package de.stefan.lang.foundationCore.api.platformdependencies

import android.content.Context
import de.stefan.lang.coreutils.api.nativecontext.ContextProvider
import de.stefan.lang.foundationCore.api.app.AppInfo
import de.stefan.lang.foundationCore.api.resources.AppResourceProvider

actual class PlatformDependencyProvider(
    val applicationContext: Context,
    actual override val appInfo: AppInfo,
    actual override val appContextProvider: ContextProvider,
    actual override val appResourceProvider: AppResourceProvider,
) : PlatformDependencyProviding
