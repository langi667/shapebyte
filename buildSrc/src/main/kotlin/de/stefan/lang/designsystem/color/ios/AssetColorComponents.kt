package de.stefan.lang.designsystem.color.ios
import kotlinx.serialization.Serializable

@Serializable
data class AssetColorComponents(
    val alpha: String,
    val red: String,
    val green: String,
    val blue: String
)