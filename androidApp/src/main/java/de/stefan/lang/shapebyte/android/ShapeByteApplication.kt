package de.stefan.lang.shapebyte.android

import android.app.Application
import de.stefan.lang.core.app.AppContextProvider
import de.stefan.lang.core.coroutines.CoroutineContextProvider
import de.stefan.lang.core.coroutines.CoroutineScopeProvider
import de.stefan.lang.core.resources.impl.AppResourceProvider
import de.stefan.lang.shapebyte.android.shared.resources.mapping.AudioMapper
import de.stefan.lang.shapebyte.app.data.PlatformDependencyProvider
import de.stefan.lang.shapebyte.di.DPI
import de.stefan.lang.core.app.AppInfo

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
                appContextProvider = AppContextProvider(this),
                appResourceProvider = AppResourceProvider(AudioMapper),
            ),
        )
    }
}
