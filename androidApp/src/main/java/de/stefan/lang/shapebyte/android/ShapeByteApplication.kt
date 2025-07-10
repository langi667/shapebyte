package de.stefan.lang.shapebyte.android

import android.app.Application
import de.stefan.lang.coreutils.api.nativecontext.ContextProvider
import de.stefan.lang.foundationCore.api.app.AppInfo
import de.stefan.lang.foundationCore.api.platformdependencies.PlatformDependencyProvider
import de.stefan.lang.foundationCore.api.resources.AppResourceProvider
import de.stefan.lang.shapebyte.SharedModule
import de.stefan.lang.shapebyte.android.shared.resources.mapping.AudioMapper

class ShapeByteApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        initializeApplication()
    }

    private fun initializeApplication() {
        SharedModule.sharedInitializationUseCase().invoke(
            PlatformDependencyProvider(
                applicationContext = this,
                appInfo = AppInfo(
                    versionName = BuildConfig.VERSION_NAME,
                    packageName = BuildConfig.APPLICATION_ID,
                    versionCode = BuildConfig.VERSION_CODE,
                    debugMode = BuildConfig.DEBUG,
                ),
                appContextProvider = ContextProvider(this),
                appResourceProvider = AppResourceProvider(
                    audioMapping = AudioMapper,
                ),
            ),
        )
    }
}
