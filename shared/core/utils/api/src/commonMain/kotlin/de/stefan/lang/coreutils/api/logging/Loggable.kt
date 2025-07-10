package de.stefan.lang.coreutils.api.logging

interface Loggable {
    val logger: Logging
    val tag: String get() = this::class.simpleName ?: "Unknown"

    fun logD(message: String) { logger.d(tag, message) }
    fun logI(message: String) { logger.i(tag, message) }
    fun logW(message: String) { logger.w(tag, message) }
    fun logE(message: String) { logger.e(tag, message) }
}
