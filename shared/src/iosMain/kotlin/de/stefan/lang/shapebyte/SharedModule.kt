package de.stefan.lang.shapebyte

import de.stefan.lang.foundationCore.platform.PlatformDependencyProvider
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