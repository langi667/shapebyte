package de.stefan.lang.shapebyte.featureCore.platformdependencies

import de.stefan.lang.coreutils.api.nativecontext.ContextProvider
import de.stefan.lang.foundationCore.api.app.AppInfo
import de.stefan.lang.foundationCore.api.resources.AppResourceProvider

interface PlatformDependencyProviding {
    val appInfo: AppInfo
    val appContextProvider: ContextProvider
    val appResourceProvider: AppResourceProvider
}
