package de.stefan.lang.shapebyte.di

import de.stefan.lang.shapebyte.app.data.PlatformDependencyProvider
import org.koin.core.context.startKoin

actual object DPI : BaseDPI() {
    actual override fun start(platformDependencies: PlatformDependencyProvider) {
        setup(platformDependencies)
        startKoin {
            modules(DPI.modules)
        }

        fileAssetLoader().setup(platformDependencies.bundle)
    }
}