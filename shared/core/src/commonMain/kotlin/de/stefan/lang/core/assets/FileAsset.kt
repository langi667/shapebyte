package de.stefan.lang.core.assets

/**
 * @param assetName the name of the asset
 * @param locatedInBundle only relevant for iOS, is true if the asset should be loaded
 * from the app bundle
 */
data class FileAsset(
    override val assetName: String,
    val locatedInBundle: Boolean = false, // TODO: check if needed
) : Asset {
    override val subPath: String = "files/$assetName"
}
