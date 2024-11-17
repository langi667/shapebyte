package de.stefan.lang.shapebyte.android

import android.content.Context
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import de.stefan.lang.shapebyte.di.DPI
import de.stefan.lang.shapebyte.utils.assets.AssetLoading
import de.stefan.lang.shapebyte.utils.assets.FileAsset
import de.stefan.lang.shapebyte.utils.assets.ImageAsset
import de.stefan.lang.shapebyte.utils.assets.impl.AssetLoaderAndroid
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import junit.framework.TestCase.assertNull
import junit.framework.TestCase.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
// TODO: try to move to androidMain
@RunWith(AndroidJUnit4::class)
class AssetLoaderTest {
    private val context: Context = InstrumentationRegistry.getInstrumentation().context

    @Test
    fun testFileLoadingSuccess() {
        val sut = createSUT()
        val content = sut.loadFile(FileAsset("test.txt"))

        assertEquals("Hallo Welt", content)
    }

    @Test
    fun testFileLoadingFileNotExists() {
        val sut = createSUT()
        val content = sut.loadFile(FileAsset("doesnotexists.txt"))

        assertTrue(content.isBlank())
    }

    @Test
    fun testImageLoadingSuccess() {
        val sut = createSUT() as AssetLoaderAndroid
        val content = sut.loadImageAsset(ImageAsset("logo.png"), context)

        assertNotNull(content)
    }

    @Test
    fun testImageLoadingImageNotExists() {
        val sut = createSUT() as AssetLoaderAndroid
        val content = sut.loadImageAsset(ImageAsset("doesnotexists.png"), context)

        assertNull(content)
    }

    private fun createSUT(): AssetLoading {
        val loader = DPI.assetLoader()

        loader.setup(context)
        return loader
    }
}