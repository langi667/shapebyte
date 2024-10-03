package de.stefan.lang.shapebyte.android

import android.app.Application
import de.stefan.lang.shapebyte.di.commonModule
import de.stefan.lang.shapebyte.features.workout.di.workoutModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class ShapeByteApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin()
    }

    private fun startKoin() {
        val modules = commonModule + workoutModule

        startKoin {
            androidContext(this@ShapeByteApplication)
            modules(modules)
        }
    }
}
