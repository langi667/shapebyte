package de.stefan.lang.shapebyte.featureCore

import de.stefan.lang.core.CoreModule
import de.stefan.lang.core.di.DIModuleDeclaration
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
        )

        FoundationCoreModule.initialize(
            appResourceProvider = data.appResourceProvider,
        )
    }
}
