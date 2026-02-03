package de.stefan.lang.shapebyte.shared

import de.stefan.lang.shapebyte.shared.initializing.InitializationUseCase

public interface SharedModuleContract {
    public fun sharedInitializationUseCase(): InitializationUseCase
}
