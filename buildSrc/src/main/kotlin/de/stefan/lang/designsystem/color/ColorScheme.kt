package de.stefan.lang.designsystem.color
import de.stefan.lang.designsystem.core.PropertyReader
import org.gradle.kotlin.dsl.provideDelegate
import kotlin.reflect.full.declaredMemberProperties

data class ColorScheme(
    val primary: Color,
    val secondary: Color,
    val background: Color,
    val inversePrimary: Color,
) {
    val all: Map<String, Color> by lazy {
        val colors: Map<String, Color> = PropertyReader.read(this)
        colors
    }

    val colors: Map<String, String> by lazy {
        all.mapValues { it.value.hex }
    }

    fun get(key: String): Color? {
        return all[key]
    }
}
