package de.stefan.lang.foundationCore.assets.mocks

import de.stefan.lang.foundationCore.assets.FileAsset
import de.stefan.lang.foundationCore.assets.FileAssetLoading

class FileAssetLoaderMock : FileAssetLoading {
    private val mockedContent = mutableMapOf<FileAsset, String>()

    fun mockFileContent(fileAsset: FileAsset, fileContent: String) {
        mockedContent[fileAsset] = fileContent
    }

    override fun loadFile(fileAsset: FileAsset): String {
        return mockedContent[fileAsset] ?: ""
    }
}
