package de.stefan.lang.foundation.core.contract.assets

interface FileAssetLoader {
    fun loadFile(fileAsset: FileAsset): String
}
