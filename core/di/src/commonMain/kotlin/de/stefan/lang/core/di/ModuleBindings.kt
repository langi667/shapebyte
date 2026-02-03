package de.stefan.lang.core.di

import org.koin.core.module.Module
import org.koin.dsl.ModuleDeclaration
import org.koin.dsl.module

open class ModuleBindings(
    allEnvironments: ModuleDeclaration,
    productionEnvironmentOnly: ModuleDeclaration = {},
    testEnvironmentOnly: ModuleDeclaration = {},
) : FeatureGraph {

    override val productionDiModule: Module = module {
        allEnvironments()
        productionEnvironmentOnly()
    }

    override val testDiModule: Module = module {
        allEnvironments()
        testEnvironmentOnly()
    }
}
