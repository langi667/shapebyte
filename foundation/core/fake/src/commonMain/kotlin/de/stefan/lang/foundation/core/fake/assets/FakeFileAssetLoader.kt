package de.stefan.lang.foundation.core.fake.assets

import de.stefan.lang.foundation.core.contract.assets.FileAsset
import de.stefan.lang.foundation.core.contract.assets.FileAssetLoading

class FakeFileAssetLoader : FileAssetLoading {
    private val content = mutableMapOf<FileAsset, String>()

    fun addFile(fileAsset: FileAsset, fileContent: String) {
        content[fileAsset] = fileContent
    }

    override fun loadFile(fileAsset: FileAsset): String {
        return content[fileAsset] ?: ""
    }
}
