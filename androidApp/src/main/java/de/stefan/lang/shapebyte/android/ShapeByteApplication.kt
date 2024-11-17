package de.stefan.lang.shapebyte.android

import android.app.Application
import de.stefan.lang.shapebyte.di.DPI
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class ShapeByteApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin()
    }

    private fun startKoin() {
        val modules = DPI.modules

        startKoin {
            androidContext(this@ShapeByteApplication)
            modules(modules)
        }

        DPI.assetLoader().setup(this)
    }
}
