package de.stefan.lang.shapebyte.utils.dicore

import org.koin.core.component.KoinComponent
import org.koin.core.module.Module
import org.koin.dsl.ModuleDeclaration
import org.koin.dsl.module

interface DIModule : KoinComponent {
    val module: Module
    val testModule: Module
}

open class DIModuleDeclaration(
    allEnvironments: ModuleDeclaration,
    appEnvironmentOnly: ModuleDeclaration = {},
    testEnvironmentOnly: ModuleDeclaration = {},
) : DIModule {

    override val module: Module = module {
        allEnvironments()
        appEnvironmentOnly()
    }

    override val testModule: Module = module {
        allEnvironments()
        testEnvironmentOnly()
    }
}
