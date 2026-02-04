package de.stefan.lang.foundation.core.contract.assets

/**
 * @param assetName the name of the asset
 * @param locatedInBundle only relevant for iOS, is true if the asset should be loaded
 * from the app bundle
 */
public data class FileAsset public constructor(
    override val assetName: String,
    public val locatedInBundle: Boolean = false, // TODO: check if needed
) : Asset {
    override val subPath: String = "files/$assetName"
}
