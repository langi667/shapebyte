package de.stefan.lang.coreutils.di

import org.koin.core.component.KoinComponent
import org.koin.core.module.Module

interface DIModule : KoinComponent {
    val module: Module
    val testModule: Module
}

open class RootDIModule(
    private val modules: List<DIModule>,
) : DIModule {
    override val module: Module = this.joinModules(modules.map { it.module })
    override val testModule: Module = this.joinModules(modules.map { it.testModule })
}

fun DIModule.joinModules(vararg modules: Module): Module {
    return this.joinModules(modules.toList())
}

fun DIModule.joinModules(modules: List<Module>): Module {
    return Module().apply {
        includes(modules)
    }
}
