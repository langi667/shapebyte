package de.stefan.lang.foundationCore.api.platformdependencies

import de.stefan.lang.coreutils.api.nativecontext.ContextProvider
import de.stefan.lang.foundationCore.api.app.AppInfo
import de.stefan.lang.foundationCore.api.resources.AppResourceProvider

class PlatformDependencyProviderMock(
    override val appContextProvider: ContextProvider = ContextProvider(Any()),
) : PlatformDependencyProviding {
    override val appInfo: AppInfo = AppInfo(
        packageName = "de.stefan.lang.shapebyte",
        versionName = "1.0",
        versionCode = 0,
        debugMode = true,
    )

    override val appResourceProvider: AppResourceProvider = AppResourceProvider()
}
