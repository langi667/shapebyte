package de.stefan.lang.foundation.core.implementation.assets

import android.content.Context
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import de.stefan.lang.coretest.CoreTest
import de.stefan.lang.coreutils.contract.nativecontext.ContextProvider
import de.stefan.lang.foundation.core.contract.assets.FileAsset
import de.stefan.lang.foundation.core.contract.assets.FileAssetLoader
import de.stefan.lang.utils.logging.LoggingModule
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.component.get
import org.koin.core.module.Module
import org.koin.test.KoinTest

@RunWith(AndroidJUnit4::class)
class FileAssetLoaderTest: CoreTest(), KoinTest {
    override val testModules: List<Module> = listOf(
        LoggingModule.testModules
    )

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

    private fun createSUT(): FileAssetLoader {
        val loader = FileAssetLoaderImpl(
            appContextProvider = ContextProvider(context),
            logging = get()
        )
        return loader
    }
}
