package de.stefan.lang.shapebyte.di

import de.stefan.lang.shapebyte.app.domain.AppInitializationUseCase

interface AppInitializationProviding {
    fun appInitializerUseCase(): AppInitializationUseCase
}
