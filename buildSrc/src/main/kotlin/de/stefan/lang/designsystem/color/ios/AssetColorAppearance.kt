package de.stefan.lang.designsystem.color.ios

import kotlinx.serialization.Serializable

@Serializable
data class AssetColorAppearance(
    val appearance: String,
    val value: String
) {
    companion object {
        val Dark = AssetColorAppearance(appearance = "luminosity", value = "dark")
    }
}