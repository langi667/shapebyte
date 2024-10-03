package de.stefan.lang.shapebyte.utils.mocks

import de.stefan.lang.shapebyte.utils.Logging

class MockLogger : Logging {
    override fun d(tag: String, message: String) { /* No op */ }
    override fun i(tag: String, message: String) { /* No op */ }
    override fun w(tag: String, message: String) { /* No op */ }
    override fun e(tag: String, message: String) { /* No op */ }
}
