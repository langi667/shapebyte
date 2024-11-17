package de.stefan.lang.shapebyte.utils.assets.mocks

import de.stefan.lang.shapebyte.utils.assets.AssetLoading
import de.stefan.lang.shapebyte.utils.assets.FileAsset

class AssetLoaderMock : AssetLoading {
    private val mockedContent = mutableMapOf<FileAsset, String>()

    fun mockFileContent(fileAsset: FileAsset, fileContent: String) {
        mockedContent[fileAsset] = fileContent
    }

    override fun loadFile(fileAsset: FileAsset): String {
        return mockedContent[fileAsset] ?: ""
    }

    override fun setup(context: Any?) {
        /* No op */
    }
}
