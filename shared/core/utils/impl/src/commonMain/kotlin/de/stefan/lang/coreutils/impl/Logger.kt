package de.stefan.lang.coreutils.impl

import de.stefan.lang.coreutils.api.logging.Logging

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
expect class Logger() : Logging {
    override fun d(tag: String, message: String)
    override fun i(tag: String, message: String)
    override fun w(tag: String, message: String)
    override fun e(tag: String, message: String)
}
