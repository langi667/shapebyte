package de.stefan.lang.shapebyte

import de.stefan.lang.shapebyte.initializing.PlatformDependencyProvider
import de.stefan.lang.shapebyte.di.BaseSharedModule
import org.koin.core.context.startKoin

actual object SharedModule : BaseSharedModule() {
    actual override fun start(platformDependencies: PlatformDependencyProvider) {
        setup(platformDependencies)
        startKoin {
            modules(modules)
        }
    }
}