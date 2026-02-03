package de.stefan.lang.utils.logging.contract

public interface RecordingLogger : Logger {
    public val latestRecordLog: RecordLog?
}
