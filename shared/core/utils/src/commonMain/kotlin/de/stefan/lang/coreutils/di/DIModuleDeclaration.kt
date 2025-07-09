package de.stefan.lang.coreutils.di

import org.koin.core.module.Module
import org.koin.dsl.ModuleDeclaration
import org.koin.dsl.module

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
