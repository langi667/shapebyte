package de.stefan.lang.foundationCore.api.platformdependencies

import de.stefan.lang.coreutils.api.nativecontext.ContextProvider
import de.stefan.lang.foundationCore.api.app.AppInfo
import de.stefan.lang.foundationCore.api.resources.AppResourceProvider

expect class PlatformDependencyProvider : PlatformDependencyProviding {
    override val appInfo: AppInfo
    override val appContextProvider: ContextProvider
    override val appResourceProvider: AppResourceProvider
}
