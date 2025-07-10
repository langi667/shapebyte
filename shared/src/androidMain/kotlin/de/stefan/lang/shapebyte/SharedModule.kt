package de.stefan.lang.shapebyte

import de.stefan.lang.foundationCore.api.platformdependencies.PlatformDependencyProvider
import de.stefan.lang.foundationCore.api.platformdependencies.PlatformDependencyProviding
import de.stefan.lang.shapebyte.di.BaseSharedModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext

actual object SharedModule : BaseSharedModule() {
    actual override fun start(platformDependencies: PlatformDependencyProviding) {
        val platformDependenciesAndroid = requireNotNull(platformDependencies as? PlatformDependencyProvider)
        setup(platformDependenciesAndroid)

        GlobalContext.startKoin {
            androidContext(platformDependenciesAndroid.applicationContext)
            modules(modules)
        }
    }
}
