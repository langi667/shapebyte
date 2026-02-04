package de.stefan.lang.foundation.core.contract.assets

public sealed interface Asset {
    public val assetName: String
    public val subPath: String // for example files/$assetName or images/$assetName

    public val fileName: String get() = assetName.substringAfterLast('/')
    public val fileEnding: String get() = fileName.substringAfterLast('.')
    public val fileNameWithoutEnding: String get() = fileName.substringBeforeLast('.')
}
