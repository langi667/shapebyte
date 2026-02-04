package de.stefan.lang.foundation.core.contract.assets

import de.stefan.lang.coretest.CoreTest
import kotlin.test.Test
import kotlin.test.assertEquals

public class AssetTest : CoreTest() {
    @Test
    fun `Test values`() {
        val fileNameWithoutEnding = "test"
        val fileEnding = "txt"
        val fileName = "$fileNameWithoutEnding.$fileEnding"

        val assetName = "subpath/$fileName"
        val subPath = "files/$assetName"

        val sut = FileAsset(assetName)

        assertEquals(assetName, sut.assetName)
        assertEquals(fileName, sut.fileName)
        assertEquals(fileEnding, sut.fileEnding)

        assertEquals(fileEnding, sut.fileEnding)
        assertEquals(fileNameWithoutEnding, sut.fileNameWithoutEnding)
        assertEquals(subPath, sut.subPath)
    }
}
