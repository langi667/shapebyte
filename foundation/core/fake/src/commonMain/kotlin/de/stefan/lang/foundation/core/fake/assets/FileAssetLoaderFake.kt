package de.stefan.lang.foundation.core.fake.assets

import de.stefan.lang.foundation.core.contract.assets.FileAsset
import de.stefan.lang.foundation.core.contract.assets.FileAssetLoader

public class FileAssetLoaderFake : FileAssetLoader {
    private val content: MutableMap<FileAsset, String> = mutableMapOf()

    public fun addFile(fileAsset: FileAsset, fileContent: String) {
        content[fileAsset] = fileContent
    }

    override fun loadFile(fileAsset: FileAsset): String {
        return content[fileAsset] ?: ""
    }
}
