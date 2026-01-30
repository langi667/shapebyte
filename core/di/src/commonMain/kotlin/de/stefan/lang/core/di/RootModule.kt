package de.stefan.lang.core.di

import org.koin.core.module.Module
import org.koin.dsl.ModuleDeclaration

/**
 * Aggregates local bindings ([bindings]) with other [dependencies] into a single module and test module.
 */
open class RootModule(
    /**
     * Local bindings for this module. Basically here you provide all instances that this module offers.
     */
    bindings: ModuleBindings = ModuleBindings(allEnvironments = {}),

    /**
     * Contains list of dependent [FeatureGraph]s whose modules should be included in this module.
     */
    dependencies: List<FeatureGraph>,
) : FeatureGraph {
    private val allModules = listOf(bindings) + dependencies
    override val productionModules: Module = this.joinModules(allModules.map { it.productionModules })
    override val testModules: Module = this.joinModules(allModules.map { it.testModules })

    constructor(dependencies: List<FeatureGraph>) : this(
        bindings = ModuleBindings(allEnvironments = {}),
        dependencies = dependencies,
    )

    constructor(
        globalBindings: ModuleDeclaration = {},
        productionBindings: ModuleDeclaration = {},
        testBindings: ModuleDeclaration = {},
        dependencies: List<FeatureGraph> = emptyList(),
    ) : this(
        bindings = ModuleBindings(
            allEnvironments = globalBindings,
            appEnvironmentOnly = productionBindings,
            testEnvironmentOnly = testBindings,
        ),
        dependencies = dependencies,
    )
}

private fun FeatureGraph.joinModules(modules: List<Module>): Module {
    return Module().apply {
        includes(modules)
    }
}
