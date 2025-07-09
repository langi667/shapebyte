package de.stefan.lang.coreutils.logging.mocks

import de.stefan.lang.coreutils.logging.Logging

class SilentLogger : Logging {
    override fun d(tag: String, message: String) { /* No op */ }
    override fun i(tag: String, message: String) { /* No op */ }
    override fun w(tag: String, message: String) { /* No op */ }
    override fun e(tag: String, message: String) { /* No op */ }
}
