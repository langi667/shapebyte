package de.stefan.lang.di

import java.io.File
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
    className: String = "Dependencies",
    excludes: List<String> = emptyList(),
    transitive: Boolean = false,
    isRootAggregator: Boolean = false,
) {
    val generatedDir = layout.buildDirectory.dir("generated/di/$className")
    val taskName = "generate${className}".replace(Regex("[^A-Za-z0-9]"), "")
    val taskProvider: TaskProvider<GenerateDiDependenciesTask> =
        tasks.register(taskName, GenerateDiDependenciesTask::class.java) {
            this.packageName.set(packageName)
            this.className.set(className)
            outputDir.set(generatedDir)
            this.rootAggregator.set(isRootAggregator)
            notCompatibleWithConfigurationCache("Generates DI dependency sources by inspecting project configurations")
        }

    afterEvaluate {
        val resolvedModuleClasses = resolveDiModuleDependencies(excludes, transitive)
        taskProvider.configure {
            moduleDependencies.set(resolvedModuleClasses)
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
    className: String = "Dependencies",
    excludes: List<String> = emptyList(),
    transitive: Boolean = false,
    contractClassName: String? = null,
    isRootAggregator: Boolean = false,
) {
    val resolvedContractClass = contractClassName ?: inferContractClass(moduleClassName)
    diModule {
        moduleClass.set(moduleClassName)
        contractClass.set(resolvedContractClass)
        this.isRootAggregator.set(isRootAggregator)
    }
    configureDiDependencies(
        packageName = packageName,
        className = className,
        excludes = excludes,
        transitive = transitive,
        isRootAggregator = isRootAggregator,
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

private fun Project.resolveDiModuleDependencies(
    excludes: List<String>,
    transitive: Boolean,
): List<ModuleDependency> {
    val apiConfigurations = listOf("commonMainApi")
    val implementationConfigurations = listOf("commonMainImplementation")
    val configurationNames = apiConfigurations + implementationConfigurations
    val exclusions = excludes.toSet()
    fun ensureEvaluated(project: Project) {
        if (project != this@resolveDiModuleDependencies) {
            this@resolveDiModuleDependencies.evaluationDependsOn(project.path)
        }
    }

    if (!transitive) {
        val result = linkedSetOf<ModuleDependency>()
        configurationNames.forEach { configName ->
            val configuration = configurations.findByName(configName) ?: return@forEach
            configuration.dependencies.withType(ProjectDependency::class.java).forEach { dependency ->
                val depPath = dependency.path
                val depProject = project(depPath)
                if (!exclusions.contains(depPath)) {
                    ensureEvaluated(depProject)
                    depProject.extensions.findByType(DiModuleExtension::class.java)?.let { extension ->
                        val moduleClass = extension.moduleClass.orNull
                        val contractClass = extension.contractClass.orNull
                        if (moduleClass != null && contractClass != null) {
                            result += ModuleDependency(moduleClass, contractClass)
                        }
                    }
                }
            }
        }
        return result.toList()
    }

    val result = linkedSetOf<ModuleDependency>()
    val visited = mutableSetOf<Project>()

    fun addModuleIfPresent(project: Project): Boolean {
        ensureEvaluated(project)
        val extension = project.extensions.findByType(DiModuleExtension::class.java)
        val moduleClass = extension?.moduleClass?.orNull
        val contractClass = extension?.contractClass?.orNull
        if (moduleClass != null && contractClass != null) {
            result += ModuleDependency(moduleClass, contractClass)
            return true
        }
        return false
    }

    fun visitApi(project: Project) {
        this@resolveDiModuleDependencies.evaluationDependsOn(project.path)
        if (!visited.add(project)) return
        if (addModuleIfPresent(project)) return
        apiConfigurations.forEach { configName ->
            val configuration = project.configurations.findByName(configName) ?: return@forEach
            configuration.dependencies.withType(ProjectDependency::class.java).forEach { dependency ->
                val depPath = dependency.path
                val depProject = project(depPath)

                if (!exclusions.contains(depPath)) {
                    visitApi(depProject)
                }
            }
        }
    }

    implementationConfigurations.forEach { configName ->
        val configuration = configurations.findByName(configName) ?: return@forEach
        configuration.dependencies.withType(ProjectDependency::class.java).forEach { dependency ->
            val depPath = dependency.path
            val depProject = project(depPath)
            if (!exclusions.contains(depPath)) {
                addModuleIfPresent(depProject)
            }
        }
    }

    apiConfigurations.forEach { configName ->
        val configuration = configurations.findByName(configName) ?: return@forEach
        configuration.dependencies.withType(ProjectDependency::class.java).forEach { dependency ->
            val depPath = dependency.path
            val depProject = project(depPath)

            if (!exclusions.contains(depPath)) {
                visitApi(depProject)
            }
        }
    }

    return result.toList()
}

private fun Project.inferContractClass(moduleClassName: String): String {
    val explicitFile = findModuleSourceFile(moduleClassName)
        ?: error("Unable to locate source file for $moduleClassName")
    val source = explicitFile.readText()
    val contractRef = parseContractReference(source)
        ?: error("Unable to detect contract interface for $moduleClassName")
    if (contractRef.contains('.')) {
        return contractRef
    }
    val imports = extractImportMap(source)
    return imports[contractRef]
        ?: run {
            val packageName = moduleClassName.substringBeforeLast('.')
            "$packageName.$contractRef"
        }
}

private fun Project.findModuleSourceFile(moduleClassName: String): File? {
    val packageName = moduleClassName.substringBeforeLast('.')
    val simpleName = moduleClassName.substringAfterLast('.')
    val relativePath = packageName.replace('.', '/') + "/$simpleName.kt"
    val candidateDirs = listOf(
        "src/commonMain/kotlin",
        "src/main/kotlin",
        "src/androidMain/kotlin",
        "src/jvmMain/kotlin",
        "src/iosMain/kotlin",
    )
    candidateDirs.forEach { dir ->
        val file = projectDir.resolve("$dir/$relativePath")
        if (file.exists()) {
            return file
        }
    }
    return null
}

private fun parseContractReference(source: String): String? {
    val marker = listOf("Module(", "RootModule(")
        .map { it to source.indexOf(it) }
        .filter { it.second >= 0 }
        .minByOrNull { it.second }
        ?: return null
    val start = marker.second + marker.first.length - 1
    if (start == -1) return null
    var depth = 0
    var closingIndex = -1
    for (index in start until source.length) {
        val ch = source[index]
        if (ch == '(') {
            depth++
        } else if (ch == ')') {
            depth--
            if (depth == 0) {
                closingIndex = index
                break
            }
        }
    }
    if (closingIndex == -1) return null
    val after = source.substring(closingIndex + 1)
    val builder = StringBuilder()
    var started = false
    for (ch in after) {
        if (!started) {
            if (ch.isWhitespace() || ch == ',') {
                continue
            }
            if (ch == '{') {
                break
            }
            if (ch == '.' || ch.isJavaIdentifierPart()) {
                builder.append(ch)
                started = true
            } else {
                break
            }
        } else {
            if (ch == '.' || ch.isJavaIdentifierPart()) {
                builder.append(ch)
            } else {
                break
            }
        }
    }
    return builder.toString().takeIf { it.isNotEmpty() }
}

private fun extractImportMap(source: String): Map<String, String> {
    val regex = Regex("import\\s+([A-Za-z0-9_.]+)")
    return regex.findAll(source).associate { match ->
        val fqcn = match.groupValues[1]
        val simple = fqcn.substringAfterLast('.')
        simple to fqcn
    }
}
