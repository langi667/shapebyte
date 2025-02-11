package de.stefan.lang.shapebyte

import de.stefan.lang.foundationCore.platform.PlatformDependencyProvider
import de.stefan.lang.shapebyte.di.BaseSharedModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext

actual object SharedModule : BaseSharedModule() {
    actual override fun start(platformDependencies: PlatformDependencyProvider) {
        setup(platformDependencies)
        GlobalContext.startKoin {
            androidContext(platformDependencies.applicationContext)
            modules(modules)
        }
    }
}
