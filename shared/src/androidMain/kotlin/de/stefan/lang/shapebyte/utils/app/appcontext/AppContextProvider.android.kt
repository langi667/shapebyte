package de.stefan.lang.shapebyte.utils.app.appcontext

import android.content.Context

actual class AppContextProvider actual constructor(appContext: Any) {
    val androidContext: Context = appContext as Context
}
