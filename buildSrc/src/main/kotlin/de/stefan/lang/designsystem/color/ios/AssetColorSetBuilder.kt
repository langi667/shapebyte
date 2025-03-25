package de.stefan.lang.designsystem.color.ios

import de.stefan.lang.designsystem.color.Color

class AssetColorSetBuilder {
    private var color: Color?  = null
    private var darkColor: Color? = null

    fun setColor(color: Color?): AssetColorSetBuilder {
        this.color = color
        return this
    }

    fun setDarkColor(darkColor: Color?): AssetColorSetBuilder {
        this.darkColor = darkColor
        return this
    }

    fun build(): AssetColorSet?  {
        val assetColor = AssetColorData
            .builder()
            .set(color)
            .build() ?: return null

        val colors = mutableListOf(assetColor)

        val darkAssetColor = AssetColorData
            .builder()
            .set(darkColor)
            .setIsDark(true)
            .build()

        if (darkAssetColor != null) {
            colors.add(darkAssetColor)
        }

        return AssetColorSet(
            colors = colors,
            info = AssetColorSetInfo.xcode,
        )
    }
}