package de.stefan.lang.shapebyte.di

import de.stefan.lang.shapebyte.initializing.SharedInitializationUseCase

interface SharedInitializationProviding {
    fun sharedInitializationUseCase(): SharedInitializationUseCase
}
