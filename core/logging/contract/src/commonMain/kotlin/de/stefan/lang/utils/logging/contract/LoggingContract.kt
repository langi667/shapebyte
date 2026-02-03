package de.stefan.lang.utils.logging.contract

public interface LoggingContract {
    public fun logger(): Logger
    public fun recordingLogger(): RecordingLogger
}
