package de.stefan.lang.shapebyte.shared

import platform.Foundation.NSLog

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual class Logger {
    actual fun d(tag: String, message: String) {
        log("Debug", tag, message)
    }

    actual fun i(tag: String, message: String) {
        log("Info", tag, message)
    }

    actual fun w(tag: String, message: String) {
        log("Warning", tag, message)
    }

    actual fun e(tag: String, message: String) {
        log("Error", tag, message)
    }

    private fun log(level: String, tag: String, message: String) {
        NSLog("[$level] [$tag] $message")
    }
}