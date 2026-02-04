package de.stefan.lang.foundation.core.contract.platformdependencies

import de.stefan.lang.coreutils.contract.nativecontext.ContextProvider
import de.stefan.lang.foundation.core.contract.app.AppInfo
import de.stefan.lang.foundation.core.contract.resources.AppResourceProvider

public class PlatformDependencyProviderMock public constructor(
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
