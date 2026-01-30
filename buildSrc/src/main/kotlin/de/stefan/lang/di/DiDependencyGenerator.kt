package de.stefan.lang.di

import org.gradle.api.NamedDomainObjectContainer
import org.gradle.api.Project
import org.gradle.api.artifacts.ProjectDependency
import org.gradle.api.tasks.TaskProvider

fun Project.diModule(configuration: DiModuleExtension.() -> Unit) {
    val extension = extensions.findByType(DiModuleExtension::class.java)
        ?: extensions.create("diModule", DiModuleExtension::class.java, this.objects)
    extension.configuration()
}

fun Project.configureDiDependencies(
    packageName: String = defaultGeneratedPackage(),
    className: String = "GeneratedDependencies",
    excludes: List<String> = emptyList(),
    transitive: Boolean = false,
) {
    val generatedDir = layout.buildDirectory.dir("generated/di/$className")
    val taskName = "generate${className}".replace(Regex("[^A-Za-z0-9]"), "")
    val taskProvider: TaskProvider<GenerateDiDependenciesTask> =
        tasks.register(taskName, GenerateDiDependenciesTask::class.java) {
            this.packageName.set(packageName)
            this.className.set(className)
            outputDir.set(generatedDir)
            notCompatibleWithConfigurationCache("Generates DI dependency sources by inspecting project configurations")
        }

    afterEvaluate {
        val resolvedModuleClasses = resolveDiModuleClasses(excludes, transitive)
        taskProvider.configure {
            moduleClasses.set(resolvedModuleClasses)
        }
    }

    attachGeneratedSources(taskProvider)

    tasks.matching { it.name == "compileKotlinMetadata" }.configureEach {
        dependsOn(taskProvider)
    }
}

fun Project.configureDi(
    moduleClassName: String,
    packageName: String = defaultGeneratedPackage(),
    className: String = "GeneratedDependencies",
    excludes: List<String> = emptyList(),
    transitive: Boolean = false,
) {
    diModule {
        moduleClass.set(moduleClassName)
    }
    configureDiDependencies(
        packageName = packageName,
        className = className,
        excludes = excludes,
        transitive = transitive,
    )
}

private fun Project.attachGeneratedSources(taskProvider: TaskProvider<GenerateDiDependenciesTask>) {
    val kotlinExtension = extensions.findByName("kotlin") ?: return
    val sourceSets = kotlinExtension.javaClass.methods
        .firstOrNull { it.name == "getSourceSets" && it.parameterCount == 0 }
        ?.invoke(kotlinExtension) as? NamedDomainObjectContainer<*>
        ?: return
    val commonSourceSet = sourceSets.findByName("commonMain") ?: return
    val kotlinSource = commonSourceSet.javaClass.methods
        .firstOrNull { it.name == "getKotlin" && it.parameterCount == 0 }
        ?.invoke(commonSourceSet)
        ?: return
    val srcDirMethod = kotlinSource.javaClass.methods
        .firstOrNull { it.name == "srcDir" && it.parameterCount == 1 }
        ?: return
    srcDirMethod.invoke(kotlinSource, taskProvider.map { it.outputDir })
}

private fun Project.defaultGeneratedPackage(): String {
    val base = nameFromPath().ifBlank { "module" }
    return "de.stefan.lang.shapebyte.$base.generated"
}

private fun Project.nameFromPath(): String =
    path.trim(':')
        .split(':')
        .filter { it.isNotBlank() }
        .joinToString(".")

private fun Project.resolveDiModuleClasses(excludes: List<String>, transitive: Boolean): List<String> {
    val apiConfigurations = listOf("commonMainApi")
    val implementationConfigurations = listOf("commonMainImplementation")
    val configurationNames = apiConfigurations + implementationConfigurations
    val exclusions = excludes.toSet()
    if (!transitive) {
        val result = linkedSetOf<String>()
        configurationNames.forEach { configName ->
            val configuration = configurations.findByName(configName) ?: return@forEach
            configuration.dependencies.withType(ProjectDependency::class.java).forEach { dependency ->
                val depProject = dependency.dependencyProject
                val depPath = depProject.path
                if (!exclusions.contains(depPath)) {
                    depProject.extensions.findByType(DiModuleExtension::class.java)?.moduleClass?.orNull?.let {
                        result += it
                    }
                }
            }
        }
        return result.toList()
    }

    val result = linkedSetOf<String>()
    val visited = mutableSetOf<Project>()

    fun addModuleIfPresent(project: Project): Boolean {
        val extension = project.extensions.findByType(DiModuleExtension::class.java)
        val moduleClass = extension?.moduleClass?.orNull
        if (moduleClass != null) {
            result += moduleClass
            return true
        }
        return false
    }

    fun visitApi(project: Project) {
        this@resolveDiModuleClasses.evaluationDependsOn(project.path)
        if (!visited.add(project)) return
        if (addModuleIfPresent(project)) return
        apiConfigurations.forEach { configName ->
            val configuration = project.configurations.findByName(configName) ?: return@forEach
            configuration.dependencies.withType(ProjectDependency::class.java).forEach { dependency ->
                val depProject = dependency.dependencyProject
                val depPath = depProject.path
                if (!exclusions.contains(depPath)) {
                    visitApi(depProject)
                }
            }
        }
    }

    implementationConfigurations.forEach { configName ->
        val configuration = configurations.findByName(configName) ?: return@forEach
        configuration.dependencies.withType(ProjectDependency::class.java).forEach { dependency ->
            val depProject = dependency.dependencyProject
            val depPath = depProject.path
            if (!exclusions.contains(depPath)) {
                addModuleIfPresent(depProject)
            }
        }
    }

    apiConfigurations.forEach { configName ->
        val configuration = configurations.findByName(configName) ?: return@forEach
        configuration.dependencies.withType(ProjectDependency::class.java).forEach { dependency ->
            val depProject = dependency.dependencyProject
            val depPath = depProject.path
            if (!exclusions.contains(depPath)) {
                visitApi(depProject)
            }
        }
    }

    return result.toList()
}
