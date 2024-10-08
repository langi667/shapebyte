package de.stefan.lang.shapebyte.android

import android.app.Application
import de.stefan.lang.shapebyte.di.CommonMainModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class ShapeByteApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin()
    }

    private fun startKoin() {
        val modules = CommonMainModules

        startKoin {
            androidContext(this@ShapeByteApplication)
            modules(modules)
        }
    }
}
