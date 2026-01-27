package de.stefan.lang.core.di

import org.koin.core.component.KoinComponent
import org.koin.core.module.Module

interface DIModule : KoinComponent {
    val module: Module
    val testModules: Module
}

/**
 * Aggregates local bindings ([providedInstances]) with other [dependencies] into a single module and test module.
 */
open class RootDIModule(
    providedInstances: DIModuleDeclaration = DIModuleDeclaration(allEnvironments = {}),
    dependencies: List<DIModule>,
) : DIModule {
    private val allModules = listOf(providedInstances) + dependencies
    override val module: Module = this.joinModules(allModules.map { it.module })
    override val testModules: Module = this.joinModules(allModules.map { it.testModules })

    constructor(dependencies: List<DIModule>) : this(
        providedInstances = DIModuleDeclaration(allEnvironments = {}),
        dependencies = dependencies,
    )
}

private fun DIModule.joinModules(modules: List<Module>): Module {
    return Module().apply {
        includes(modules)
    }
}
