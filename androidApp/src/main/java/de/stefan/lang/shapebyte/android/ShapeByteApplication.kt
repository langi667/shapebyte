package de.stefan.lang.shapebyte.android

import android.app.Application
import de.stefan.lang.shapebyte.app.data.PlatformDependencyProvider
import de.stefan.lang.shapebyte.di.DPI
import de.stefan.lang.shapebyte.utils.coroutines.CoroutineContextProvider
import de.stefan.lang.shapebyte.utils.coroutines.CoroutineScopeProvider

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
            ),
        )
    }
}
