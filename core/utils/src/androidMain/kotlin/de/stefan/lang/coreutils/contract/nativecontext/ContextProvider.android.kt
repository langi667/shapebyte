package de.stefan.lang.coreutils.contract.nativecontext

import android.content.Context

public actual class ContextProvider actual constructor(public val appContext: Any) {
    public val androidContext: Context
        get() {
            return appContext as Context
        }
}
