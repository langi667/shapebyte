package de.stefan.lang.shapebyte.app.data.mocks

import de.stefan.lang.coreutils.coroutines.CoroutineContextProvider
import de.stefan.lang.coreutils.coroutines.CoroutineContextProviding
import de.stefan.lang.coreutils.coroutines.CoroutineScopeProvider
import de.stefan.lang.coreutils.coroutines.CoroutineScopeProviding
import de.stefan.lang.coreutils.nativecontext.ContextProvider
import de.stefan.lang.foundationCore.app.AppInfo
import de.stefan.lang.foundationCore.resources.impl.AppResourceProvider
import de.stefan.lang.shapebyte.app.data.PlatformDependencyProviding

class PackageDependencyProviderMock(
    override val coroutineContextProvider: CoroutineContextProviding = CoroutineContextProvider(),
    override val coroutineScopeProviding: CoroutineScopeProviding = CoroutineScopeProvider(),
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
