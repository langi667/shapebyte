package de.stefan.lang.utils.logging.contract

public interface LoggingContract {
    public fun logger(): Logging
    public fun recordingLogger(): RecordingLogging
}
