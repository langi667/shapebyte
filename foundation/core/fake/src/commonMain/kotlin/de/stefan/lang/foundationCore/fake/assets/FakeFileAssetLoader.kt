package de.stefan.lang.foundationCore.fake.assets

import de.stefan.lang.foundationCore.api.assets.FileAsset
import de.stefan.lang.foundationCore.api.assets.FileAssetLoading

class FakeFileAssetLoader : FileAssetLoading {
    private val mockedContent = mutableMapOf<FileAsset, String>()

    fun mockFileContent(fileAsset: FileAsset, fileContent: String) {
        mockedContent[fileAsset] = fileContent
    }

    override fun loadFile(fileAsset: FileAsset): String {
        return mockedContent[fileAsset] ?: ""
    }
}
