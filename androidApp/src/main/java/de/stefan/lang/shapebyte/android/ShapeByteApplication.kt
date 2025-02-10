package de.stefan.lang.shapebyte.android

import android.app.Application
import de.stefan.lang.coreutils.coroutines.CoroutineContextProvider
import de.stefan.lang.coreutils.coroutines.CoroutineScopeProvider
import de.stefan.lang.coreutils.nativecontext.ContextProvider
import de.stefan.lang.foundationCore.app.AppInfo
import de.stefan.lang.foundationCore.resources.impl.AppResourceProvider
import de.stefan.lang.shapebyte.android.shared.resources.mapping.AudioMapper
import de.stefan.lang.shapebyte.app.data.PlatformDependencyProvider
import de.stefan.lang.shapebyte.di.DPI

class ShapeByteApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        initializeApplication()
    }

    private fun initializeApplication() {
        DPI.appInitializerUseCase().invoke(
            PlatformDependencyProvider(
                applicationContext = this,
                coroutineScopeProviding = CoroutineScopeProvider(),
                coroutineContextProvider = CoroutineContextProvider(),
                appInfo = AppInfo(
                    versionName = BuildConfig.VERSION_NAME,
                    packageName = BuildConfig.APPLICATION_ID,
                    versionCode = BuildConfig.VERSION_CODE,
                    debugMode = BuildConfig.DEBUG,
                ),
                appContextProvider = ContextProvider(this),
                appResourceProvider = AppResourceProvider(AudioMapper),
            ),
        )
    }
}
