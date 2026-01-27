package de.stefan.lang.utils.logging.implementation.silent

import de.stefan.lang.utils.logging.contract.Logging

public class SilentLogger : Logging {
    override fun d(tag: String, message: String) { /* No op */ }
    override fun i(tag: String, message: String) { /* No op */ }
    override fun w(tag: String, message: String) { /* No op */ }
    override fun e(tag: String, message: String) { /* No op */ }
}
