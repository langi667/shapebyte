package de.stefan.lang.coreutils

import de.stefan.lang.core.di.DIModuleDeclaration
import de.stefan.lang.coreutils.CoreUtilsModule.contextProvider
import de.stefan.lang.coreutils.contract.logging.Logging
import de.stefan.lang.coreutils.contract.logging.RecordingLogging
import de.stefan.lang.coreutils.contract.nativecontext.ContextProvider
import de.stefan.lang.coreutils.implementation.Logger
import de.stefan.lang.coreutils.implementation.recording.RecordingLogger
import de.stefan.lang.coreutils.implementation.silent.SilentLogger
import de.stefan.lang.coroutines.CoroutinesModule
import de.stefan.lang.coroutines.CoroutinesModuleProviding
import org.koin.core.component.get

public interface CoreUtilsModuleProviding {
    public fun logger(): Logging
    public fun recordingLogger(): RecordingLogging
}

public object CoreUtilsModule :
    DIModuleDeclaration(
        allEnvironments = {
            single<ContextProvider> { contextProvider }
            factory<RecordingLogging> { RecordingLogger(get()) }
        },
        appEnvironmentOnly = {
            single<Logging> { Logger() }
        },
        testEnvironmentOnly = {
            single<Logging> { SilentLogger() }
        },
    ),
    CoreUtilsModuleProviding,
    CoroutinesModuleProviding by CoroutinesModule {
    private lateinit var contextProvider: ContextProvider

    public fun initialize(
        contextProvider: ContextProvider,
    ) {
        this.contextProvider = contextProvider
    }

    public override fun logger(): Logging = get()
    public override fun recordingLogger(): RecordingLogging = get()
}
