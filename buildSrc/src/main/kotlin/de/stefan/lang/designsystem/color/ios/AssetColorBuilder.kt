package de.stefan.lang.designsystem.color.ios

import de.stefan.lang.designsystem.color.Color
import java.util.Locale

class AssetColorBuilder {
    private var color: Color? = null
    private var isDark: Boolean = false

    fun set(color: Color?): AssetColorBuilder {
        this.color = color
        return this
    }

    fun setIsDark(isDark: Boolean): AssetColorBuilder {
        this.isDark = isDark
        return this
    }

    fun build(): AssetColor? {
        val color = this.color ?: return null

        val components = AssetColorComponents(
            alpha = String.format(Locale.US, "%.3f", color.alphaRelative),
            red = color.red.toString(),
            green = color.green.toString(),
            blue = color.blue.toString()
        )

        val appearances: List<AssetColorAppearance> = if (isDark) {
            listOf(AssetColorAppearance.Dark)
        }
        else {
            emptyList()
        }

        val colorData = AssetColorData(
            components = components,
            colorSpace = "srgb",
        )

        return AssetColor(colorData, appearances, "universal")
    }
}