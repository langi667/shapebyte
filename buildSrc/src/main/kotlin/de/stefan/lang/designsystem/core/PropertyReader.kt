package de.stefan.lang.designsystem.core

import kotlin.reflect.full.declaredMemberProperties

object PropertyReader {
    inline fun <reified PropertyClazz, reified InstanceClazz : Any> read(
        instance: InstanceClazz,
    ): HashMap<String, PropertyClazz> {
        val properties = InstanceClazz::class.declaredMemberProperties
            .filter { it.returnType.classifier == PropertyClazz::class }

        val retVal = HashMap<String, PropertyClazz>()

        properties.forEach { currProperty ->
            val value = currProperty.get(instance) as? PropertyClazz

            if (value != null ) {
                retVal[currProperty.name] = value
            }
            else {
                println("Unable to get value from property ${currProperty.name}")
            }
        }

        return retVal

    }
}