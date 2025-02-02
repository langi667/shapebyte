package de.stefan.lang.core.assets.mocks

import de.stefan.lang.core.assets.FileAsset
import de.stefan.lang.core.assets.FileAssetLoading

class FileAssetLoaderMock : FileAssetLoading {
    private val mockedContent = mutableMapOf<FileAsset, String>()

    fun mockFileContent(fileAsset: FileAsset, fileContent: String) {
        mockedContent[fileAsset] = fileContent
    }

    override fun loadFile(fileAsset: FileAsset): String {
        return mockedContent[fileAsset] ?: ""
    }
}
