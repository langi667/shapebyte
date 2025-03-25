package de.stefan.lang.designsystem.color.ios

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AssetColorData(
    val components: AssetColorComponents,
    @SerialName("color-space")
    val colorSpace: String,
) {
    companion object {
        fun builder(): AssetColorBuilder = AssetColorBuilder()
    }
}

