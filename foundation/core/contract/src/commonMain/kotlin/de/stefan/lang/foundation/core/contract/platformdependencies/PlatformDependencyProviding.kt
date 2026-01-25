package de.stefan.lang.foundation.core.contract.platformdependencies

import de.stefan.lang.coreutils.contract.nativecontext.ContextProvider
import de.stefan.lang.foundation.core.contract.app.AppInfo
import de.stefan.lang.foundation.core.contract.resources.AppResourceProvider

interface PlatformDependencyProviding {
    val appInfo: AppInfo
    val appContextProvider: ContextProvider
    val appResourceProvider: AppResourceProvider
}
