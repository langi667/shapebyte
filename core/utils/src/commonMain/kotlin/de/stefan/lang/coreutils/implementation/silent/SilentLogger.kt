package de.stefan.lang.coreutils.implementation.silent

import de.stefan.lang.coreutils.contract.logging.Logging

internal class SilentLogger : Logging {
    override fun d(tag: String, message: String) { /* No op */ }
    override fun i(tag: String, message: String) { /* No op */ }
    override fun w(tag: String, message: String) { /* No op */ }
    override fun e(tag: String, message: String) { /* No op */ }
}
