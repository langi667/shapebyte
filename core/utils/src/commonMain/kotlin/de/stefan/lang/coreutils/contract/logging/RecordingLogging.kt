package de.stefan.lang.coreutils.contract.logging

public interface RecordingLogging : Logging {
    public val latestRecordLog: RecordLog?
}
