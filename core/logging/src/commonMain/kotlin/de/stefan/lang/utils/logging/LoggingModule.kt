package de.stefan.lang.utils.logging

import de.stefan.lang.core.di.RootModule
import de.stefan.lang.shapebyte.core.logging.generated.GeneratedDependencies
import de.stefan.lang.utils.logging.contract.Logging
import de.stefan.lang.utils.logging.contract.LoggingContract
import de.stefan.lang.utils.logging.contract.RecordingLogging
import de.stefan.lang.utils.logging.implementation.Logger
import de.stefan.lang.utils.logging.implementation.recording.RecordingLogger
import de.stefan.lang.utils.logging.implementation.silent.SilentLogger
import org.koin.core.component.get

public object LoggingModule :
    RootModule(
        globalBindings = {
            factory<RecordingLogging> { RecordingLogger(get()) }
        },
        productionBindings = {
            single<Logging> { Logger() }
        },
        testBindings = {
            single<Logging> { SilentLogger() }
        },
        dependencies = GeneratedDependencies.modules,
    ),
    LoggingContract {
    public override fun logger(): Logging = get()
    public override fun recordingLogger(): RecordingLogging = get()
}
