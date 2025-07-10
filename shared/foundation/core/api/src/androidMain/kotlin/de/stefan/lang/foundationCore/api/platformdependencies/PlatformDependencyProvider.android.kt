package de.stefan.lang.foundationCore.api.platformdependencies

import android.content.Context
import de.stefan.lang.coreutils.api.nativecontext.ContextProvider
import de.stefan.lang.foundationCore.api.app.AppInfo
import de.stefan.lang.foundationCore.api.resources.AppResourceProvider

data class PlatformDependencyProvider(
    val applicationContext: Context,
    override val appInfo: AppInfo,
    override val appContextProvider: ContextProvider,
    override val appResourceProvider: AppResourceProvider,
) : PlatformDependencyProviding
