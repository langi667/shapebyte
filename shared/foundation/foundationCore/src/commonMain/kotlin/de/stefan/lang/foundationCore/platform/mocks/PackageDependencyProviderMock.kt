package de.stefan.lang.foundationCore.platform.mocks

import de.stefan.lang.coreCoroutinesProviding.CoroutineContextProvider
import de.stefan.lang.coreCoroutinesProviding.CoroutineContextProviding
import de.stefan.lang.coreCoroutinesProviding.CoroutineScopeProvider
import de.stefan.lang.coreCoroutinesProviding.CoroutineScopeProviding
import de.stefan.lang.coreutils.nativecontext.ContextProvider
import de.stefan.lang.foundationCore.app.AppInfo
import de.stefan.lang.foundationCore.platform.PlatformDependencyProviding
import de.stefan.lang.foundationCore.resources.impl.AppResourceProvider

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
