package de.stefan.lang.foundation.core.contract.platformdependencies

import de.stefan.lang.foundation.core.contract.app.AppInfo
import de.stefan.lang.coreutils.contract.nativecontext.ContextProvider
import de.stefan.lang.foundation.core.contract.resources.AppResourceProvider

import platform.Foundation.NSBundle

actual class PlatformDependencyProvider(
    val bundle: NSBundle,
    actual override val appInfo: AppInfo,
    actual override val appContextProvider: ContextProvider,
    actual override val appResourceProvider: AppResourceProvider,
) : PlatformDependencyProviding