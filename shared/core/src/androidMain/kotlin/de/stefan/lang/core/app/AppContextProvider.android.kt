package de.stefan.lang.core.app

import android.content.Context

actual class AppContextProvider actual constructor(val appContext: Any) {
    val androidContext: Context get() {
        return appContext as Context
    }
}
