package de.stefan.lang.designsystem.color.ios

import de.stefan.lang.designsystem.color.Color
import java.io.File
import java.net.URI

class AssetColorSetCreator {
    private var color: Color?  = null
    private var darkColor: Color? = null

    fun setColor(color: Color): AssetColorSetCreator {
        this.color = color
        return this
    }

    fun setDarkColor(darkColor: Color?): AssetColorSetCreator {
        this.darkColor = darkColor
        return this
    }

    fun create(path: String, name: String) {
        val assetColorSet = AssetColorSetBuilder()
            .setColor(color)
            .setDarkColor(darkColor)
            .build() ?: return

        val serialized = AssetColorSetSerializer().serialize(assetColorSet)
        val folder = File("$path/${name}.colorset")

        folder.mkdirs()
        val file = File(folder, "Contents.json")

        file.writeText(serialized)
    }
}