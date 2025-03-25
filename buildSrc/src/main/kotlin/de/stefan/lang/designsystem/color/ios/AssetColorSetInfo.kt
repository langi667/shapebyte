package de.stefan.lang.designsystem.color.ios

import kotlinx.serialization.Serializable

@Serializable
data class AssetColorSetInfo(
    val version: String,
    val author: String
) {
    companion object {
        val xcode: AssetColorSetInfo = AssetColorSetInfo(
            version = "1",
            author = "xcode"
        )
    }
}
