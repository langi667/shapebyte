package de.stefan.lang.utils.logging.implementation

import de.stefan.lang.utils.logging.contract.Logger

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
public expect class LoggerImpl() : Logger {
    override fun d(tag: String, message: String)
    override fun i(tag: String, message: String)
    override fun w(tag: String, message: String)
    override fun e(tag: String, message: String)
}
