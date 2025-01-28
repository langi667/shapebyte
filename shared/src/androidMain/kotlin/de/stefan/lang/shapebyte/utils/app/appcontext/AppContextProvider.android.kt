package de.stefan.lang.shapebyte.utils.app.appcontext

import android.content.Context

actual class AppContextProvider actual constructor(val appContext: Any) {
    val androidContext: Context get() {
        return appContext as Context
    }
}
