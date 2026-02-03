package de.stefan.lang.utils.logging

import de.stefan.lang.shapebyte.core.logging.generated.Dependencies
import de.stefan.lang.shapebyte.core.logging.generated.Module
import de.stefan.lang.utils.logging.contract.Logger
import de.stefan.lang.utils.logging.contract.LoggingContract
import de.stefan.lang.utils.logging.contract.RecordingLogger
import de.stefan.lang.utils.logging.implementation.LoggerImpl
import de.stefan.lang.utils.logging.implementation.recording.RecordingLoggerImpl
import de.stefan.lang.utils.logging.implementation.silent.SilentLogger
import org.koin.core.component.get

public object LoggingModule :
    Module(
        globalBindings = {
            factory<RecordingLogger> { RecordingLoggerImpl(get()) }
        },
        productionBindings = {
            single<Logger> { LoggerImpl() }
        },
        testBindings = {
            single<Logger> { SilentLogger() }
        },
    ),
    LoggingContract {
    public override fun logger(): Logger = get()
    public override fun recordingLogger(): RecordingLogger = get()
}
