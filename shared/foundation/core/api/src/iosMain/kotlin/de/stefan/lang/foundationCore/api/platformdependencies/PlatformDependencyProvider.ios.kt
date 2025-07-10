package de.stefan.lang.foundationCore.api.platformdependencies

import de.stefan.lang.foundationCore.api.app.AppInfo
import de.stefan.lang.coreutils.api.nativecontext.ContextProvider
import de.stefan.lang.foundationCore.api.platformdependencies.PlatformDependencyProviding
import de.stefan.lang.foundationCore.api.resources.AppResourceProvider

import platform.Foundation.NSBundle

data class PlatformDependencyProvider(
    val bundle: NSBundle,
    override val appInfo: AppInfo,
    override val appContextProvider: ContextProvider,
    override val appResourceProvider: AppResourceProvider,
) : PlatformDependencyProviding