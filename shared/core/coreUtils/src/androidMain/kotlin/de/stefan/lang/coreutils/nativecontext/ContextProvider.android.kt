package de.stefan.lang.coreutils.nativecontext

import android.content.Context

actual class ContextProvider actual constructor(val appContext: Any) {
    val androidContext: Context
        get() {
            return appContext as Context
        }
}
