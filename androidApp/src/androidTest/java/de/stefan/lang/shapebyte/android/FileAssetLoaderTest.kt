package de.stefan.lang.shapebyte.android

import android.content.Context
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import de.stefan.lang.coreutils.logging.mocks.SilentLogger
import de.stefan.lang.coreutils.nativecontext.ContextProvider
import de.stefan.lang.foundationCore.assets.FileAsset
import de.stefan.lang.foundationCore.assets.FileAssetLoading
import de.stefan.lang.foundationCore.assets.impl.FileAssetLoader
import de.stefan.lang.shapebyte.di.DPI
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class FileAssetLoaderTest {
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

    private fun createSUT(): FileAssetLoading {
        val loader = FileAssetLoader(
            appContextProvider = ContextProvider(context),
            logging = SilentLogger()
        )

        return loader
    }
}