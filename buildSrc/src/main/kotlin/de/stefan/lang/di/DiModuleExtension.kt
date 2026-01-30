package de.stefan.lang.di

import org.gradle.api.model.ObjectFactory
import org.gradle.api.provider.Property
import javax.inject.Inject

open class DiModuleExtension @Inject constructor(objects: ObjectFactory) {
    val moduleClass: Property<String> = objects.property(String::class.java)
}
