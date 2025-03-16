package de.stefan.lang.designsystem.color
import kotlin.reflect.full.declaredMemberProperties

data class ColorScheme(
    val primary: Color,
    val secondary: Color,
    val background: Color,
    val inversePrimary: Color,
) {
    val colors: Map<String, String>
        get() {
            val properties = ColorScheme::class.declaredMemberProperties
                .filter { it.returnType.classifier == Color::class }

            val colors = HashMap<String,String>()
            properties.forEach { currColorProperty ->
                val color = currColorProperty.get(this) as? Color

                if (color != null ) {
                    colors[currColorProperty.name] = color.hex
                }
                else {
                    println("Unable to create color from Property ${currColorProperty.name}")
                }
            }

            return colors
        }
}
