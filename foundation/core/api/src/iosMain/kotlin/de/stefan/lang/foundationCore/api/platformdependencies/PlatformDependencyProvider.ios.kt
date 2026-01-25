package de.stefan.lang.foundationCore.api.platformdependencies

import de.stefan.lang.foundationCore.api.app.AppInfo
import de.stefan.lang.coreutils.contract.nativecontext.ContextProvider
import de.stefan.lang.foundationCore.api.resources.AppResourceProvider

import platform.Foundation.NSBundle

actual class PlatformDependencyProvider(
    val bundle: NSBundle,
    actual override val appInfo: AppInfo,
    actual override val appContextProvider: ContextProvider,
    actual override val appResourceProvider: AppResourceProvider,
) : PlatformDependencyProviding