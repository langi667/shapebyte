package de.stefan.lang.shapebyte

import de.stefan.lang.foundationCore.api.platformdependencies.PlatformDependencyProviding
import de.stefan.lang.shapebyte.di.BaseSharedModule
import org.koin.core.context.startKoin

actual object SharedModule : BaseSharedModule() {
    actual override fun start(platformDependencies: PlatformDependencyProviding) {
        setup(platformDependencies)
        startKoin {
            modules(modules)
        }
    }
}