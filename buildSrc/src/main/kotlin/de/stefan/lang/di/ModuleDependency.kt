package de.stefan.lang.di

import java.io.Serializable

data class ModuleDependency(
    val moduleClass: String,
    val contractClass: String,
) : Serializable
