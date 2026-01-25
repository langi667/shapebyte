package de.stefan.lang.coreutils.implementation

import android.util.Log
import de.stefan.lang.coreutils.contract.logging.Logging

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
internal actual class Logger actual constructor() : Logging {
    actual override fun d(tag: String, message: String) {
        Log.d(tag, message)
    }

    actual override fun i(tag: String, message: String) {
        Log.i(tag, message)
    }

    actual override fun w(tag: String, message: String) {
        Log.w(tag, message)
    }

    actual override fun e(tag: String, message: String) {
        Log.e(tag, message)
    }
}
