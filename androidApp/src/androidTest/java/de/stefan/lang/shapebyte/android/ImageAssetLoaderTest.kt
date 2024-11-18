package de.stefan.lang.shapebyte.android

import android.content.Context
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import de.stefan.lang.shapebyte.android.utils.assets.ImageAssetLoader
import de.stefan.lang.shapebyte.utils.assets.ImageAsset
import junit.framework.TestCase.assertNotNull
import junit.framework.TestCase.assertNull
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ImageAssetLoaderTest {
    private val context: Context = InstrumentationRegistry.getInstrumentation().context

    @Test
    fun testFileLoadingSuccess() {
        val sut = ImageAssetLoader
        val content = sut.loadImage(ImageAsset("logo.png"), context)

        assertNotNull(content)
    }


    @Test
    fun testFileLoadingFailure() {
        val sut = ImageAssetLoader
        val content = sut.loadImage(ImageAsset("doesnotexists.png"), context)

        assertNull(content)
    }
}