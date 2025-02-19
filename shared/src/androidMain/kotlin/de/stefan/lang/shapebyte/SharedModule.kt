package de.stefan.lang.shapebyte

import de.stefan.lang.shapebyte.di.BaseSharedModule
import de.stefan.lang.shapebyte.featureCore.platformdependencies.PlatformDependencyProvider
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
