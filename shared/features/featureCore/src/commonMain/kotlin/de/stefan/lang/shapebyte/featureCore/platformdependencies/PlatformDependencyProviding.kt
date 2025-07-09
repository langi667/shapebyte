package de.stefan.lang.shapebyte.featureCore.platformdependencies

import de.stefan.lang.coreutils.nativecontext.ContextProvider
import de.stefan.lang.foundationCore.app.AppInfo
import de.stefan.lang.foundationCore.resources.impl.AppResourceProvider

interface PlatformDependencyProviding {
    val appInfo: AppInfo
    val appContextProvider: ContextProvider
    val appResourceProvider: AppResourceProvider
}
