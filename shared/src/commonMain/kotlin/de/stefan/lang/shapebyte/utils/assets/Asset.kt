package de.stefan.lang.shapebyte.utils.assets

sealed interface Asset {
    val assetName: String
    val subPath: String // for example files/$assetName or images/$assetName

    val fileName: String get() = assetName.substringAfterLast('/')
    val fileEnding: String get() = fileName.substringAfterLast('.')
    val fileNameWithoutEnding: String get() = fileName.substringBeforeLast('.')
}
