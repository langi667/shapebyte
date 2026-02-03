package de.stefan.lang.utils.logging.implementation.silent

import de.stefan.lang.utils.logging.contract.Logger

public class SilentLogger : Logger {
    override fun d(tag: String, message: String) { /* No op */ }
    override fun i(tag: String, message: String) { /* No op */ }
    override fun w(tag: String, message: String) { /* No op */ }
    override fun e(tag: String, message: String) { /* No op */ }
}
