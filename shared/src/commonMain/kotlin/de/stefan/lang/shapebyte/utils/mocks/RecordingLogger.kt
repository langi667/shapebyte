package de.stefan.lang.shapebyte.utils.mocks

import de.stefan.lang.shapebyte.utils.logging.Logging

class RecordingLogger : Logging {
    data class Record(
        val tag: String,
        val level: String,
        val message: String,
    )

    val latestRecord: Record? get() = records.lastOrNull()
    private val records = mutableListOf<Record>()

    override fun d(tag: String, message: String) {
        records.add(Record(tag, "d", message))
    }

    override fun i(tag: String, message: String) {
        records.add(Record(tag, "i", message))
    }

    override fun w(tag: String, message: String) {
        records.add(Record(tag, "w", message))
    }

    override fun e(tag: String, message: String) {
        records.add(Record(tag, "e", message))
    }
}
