package de.stefan.lang.designsystem.color.ios

import kotlinx.serialization.Serializable

@Serializable
data class AssetColor(
    val color: AssetColorData,
    val appearances: List<AssetColorAppearance>,
    val idiom: String,
)
