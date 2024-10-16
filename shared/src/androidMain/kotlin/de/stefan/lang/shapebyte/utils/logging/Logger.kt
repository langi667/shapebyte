package de.stefan.lang.shapebyte.utils.logging

import android.util.Log

actual class Logger : Logging {
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
