package de.stefan.lang.core.di

import org.koin.core.component.KoinComponent
import org.koin.core.module.Module

interface DIModule : KoinComponent {
    val module: Module
    val testModule: Module
}

open class RootDIModule(
    providedModule: DIModuleDeclaration = DIModuleDeclaration(allEnvironments = {}),
    diModules: List<DIModule>,
) : DIModule {
    private val allModules = (listOf(providedModule) + diModules)
    override val module: Module = this.joinModules(allModules.map { it.module })
    override val testModule: Module = this.joinModules(allModules.map { it.testModule })

    constructor(diModules: List<DIModule>) : this(
        providedModule = DIModuleDeclaration(allEnvironments = {}),
        diModules = diModules,
    )
}

private fun DIModule.joinModules(modules: List<Module>): Module {
    return Module().apply {
        includes(modules)
    }
}
