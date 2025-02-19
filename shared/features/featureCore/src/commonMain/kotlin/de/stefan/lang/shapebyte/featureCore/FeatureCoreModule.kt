package de.stefan.lang.shapebyte.featureCore

import de.stefan.lang.core.CoreModule
import de.stefan.lang.coreutils.di.DIModuleDeclaration
import de.stefan.lang.foundationCore.FoundationCoreModule
import de.stefan.lang.shapebyte.featureCore.platformdependencies.PlatformDependencyProviding

interface FeatureCoreModuleProviding

object FeatureCoreModule :
    DIModuleDeclaration(
        allEnvironments = {
        },
        appEnvironmentOnly = {
        },
        testEnvironmentOnly = {
        },
    ),
    FeatureCoreModuleProviding {

    fun setup(
        data: PlatformDependencyProviding,
    ) {
        CoreModule.initialize(
            contextProvider = data.appContextProvider,
            coroutineContextProvider = data.coroutineContextProvider,
            coroutineScopeProviding = data.coroutineScopeProviding,
        )

        FoundationCoreModule.initialize(
            appResourceProvider = data.appResourceProvider,
        )
    }
}
