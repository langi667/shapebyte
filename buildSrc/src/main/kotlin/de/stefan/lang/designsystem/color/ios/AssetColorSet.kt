package de.stefan.lang.designsystem.color.ios

import kotlinx.serialization.Serializable

@Serializable
data class AssetColorSet(
    val colors: List<AssetColor>,
    val info: AssetColorSetInfo,
) {
}