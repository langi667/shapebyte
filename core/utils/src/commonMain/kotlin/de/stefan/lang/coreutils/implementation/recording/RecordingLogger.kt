package de.stefan.lang.coreutils.implementation.recording

import de.stefan.lang.coreutils.contract.logging.Logging
import de.stefan.lang.coreutils.contract.logging.RecordLog
import de.stefan.lang.coreutils.contract.logging.RecordingLogging
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

internal class RecordingLogger(
    private val logger: Logging,
) : RecordingLogging {

    override val latestRecordLog: RecordLog?
        get() = recordLogs.value.lastOrNull()
    private val recordLogs = MutableStateFlow(emptyList<RecordLog>())

    override fun d(tag: String, message: String) {
        logger.d(tag, message)
        recordLogs.update { it + RecordLog(tag, "d", message) }
    }

    override fun i(tag: String, message: String) {
        logger.i(tag, message)
        recordLogs.update { it + RecordLog(tag, "i", message) }
    }

    override fun w(tag: String, message: String) {
        logger.w(tag, message)
        recordLogs.update { it + RecordLog(tag, "w", message) }
    }

    override fun e(tag: String, message: String) {
        logger.e(tag, message)
        recordLogs.update { it + RecordLog(tag, "e", message) }
    }
}
