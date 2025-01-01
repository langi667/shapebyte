package de.stefan.lang.shapebyte.di

import de.stefan.lang.shapebyte.app.data.PlatformDependencyProvider
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

actual object DPI : BaseDPI() {
    actual override fun start(platformDependencies: PlatformDependencyProvider) {
        setup(platformDependencies)
        startKoin {
            androidContext(platformDependencies.applicationContext)
            modules(modules)
        }

        fileAssetLoader().setup(platformDependencies.applicationContext)
    }
}
