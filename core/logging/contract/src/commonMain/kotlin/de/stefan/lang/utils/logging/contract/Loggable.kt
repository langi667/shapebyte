package de.stefan.lang.utils.logging.contract

public interface Loggable {
    public val logger: Logging
    public val tag: String get() = this::class.simpleName ?: "Unknown"

    public fun logD(message: String) { logger.d(tag, message) }
    public fun logI(message: String) { logger.i(tag, message) }
    public fun logW(message: String) { logger.w(tag, message) }
    public fun logE(message: String) { logger.e(tag, message) }
}
