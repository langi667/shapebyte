package de.stefan.lang.utils.logging.contract

public interface RecordingLogging : Logging {
    public val latestRecordLog: RecordLog?
}
