package de.stefan.lang.designsystem.shapes

import kotlin.reflect.full.declaredMemberProperties

data class RoundedCorners(
    val small: Shape.RoundedCorner,
    val medium: Shape.RoundedCorner,
    val large: Shape.RoundedCorner,
    val extraLarge: Shape.RoundedCorner,
) {

    fun all(): HashMap<String, Shape.RoundedCorner> {
        val properties = RoundedCorners::class.declaredMemberProperties
            .filter { it.returnType.classifier == Shape.RoundedCorner::class }

        val roundedCorners = HashMap<String, Shape.RoundedCorner>()

        properties.forEach { currRoundedCorner ->
            val roundedCorner = currRoundedCorner.get(this) as? Shape.RoundedCorner

            if (roundedCorner != null ) {
                roundedCorners[currRoundedCorner.name] = roundedCorner
            }
            else {
                println("Unable to create Rounded Corner from property ${currRoundedCorner.name}")
            }
        }

        return roundedCorners
    }
}
