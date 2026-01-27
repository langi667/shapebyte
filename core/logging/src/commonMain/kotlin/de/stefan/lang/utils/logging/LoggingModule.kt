package de.stefan.lang.utils.logging

import de.stefan.lang.core.di.DIModuleDeclaration
import de.stefan.lang.utils.logging.contract.Logging
import de.stefan.lang.utils.logging.contract.LoggingContract
import de.stefan.lang.utils.logging.contract.RecordingLogging
import de.stefan.lang.utils.logging.implementation.Logger
import de.stefan.lang.utils.logging.implementation.recording.RecordingLogger
import de.stefan.lang.utils.logging.implementation.silent.SilentLogger
import org.koin.core.component.get

public object LoggingModule :
    DIModuleDeclaration(
        allEnvironments = {
            factory<RecordingLogging> { RecordingLogger(get()) }
        },
        appEnvironmentOnly = {
            single<Logging> { Logger() }
        },
        testEnvironmentOnly = {
            single<Logging> { SilentLogger() }
        },
    ),
    LoggingContract {
    public override fun logger(): Logging = get()
    public override fun recordingLogger(): RecordingLogging = get()
}
