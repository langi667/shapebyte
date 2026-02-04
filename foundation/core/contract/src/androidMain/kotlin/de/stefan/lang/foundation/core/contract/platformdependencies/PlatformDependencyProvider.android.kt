package de.stefan.lang.foundation.core.contract.platformdependencies

import android.content.Context
import de.stefan.lang.coreutils.contract.nativecontext.ContextProvider
import de.stefan.lang.foundation.core.contract.app.AppInfo
import de.stefan.lang.foundation.core.contract.resources.AppResourceProvider

public actual class PlatformDependencyProvider(
    public val applicationContext: Context,
    actual override val appInfo: AppInfo,
    actual override val appContextProvider: ContextProvider,
    actual override val appResourceProvider: AppResourceProvider,
) : PlatformDependencyProviding
