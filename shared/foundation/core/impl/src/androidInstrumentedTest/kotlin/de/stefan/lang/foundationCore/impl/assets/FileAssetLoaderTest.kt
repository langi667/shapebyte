package de.stefan.lang.foundationCore.impl.assets

import android.content.Context
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import de.stefan.lang.core.CoreModule
import de.stefan.lang.coretest.CoreTest
import de.stefan.lang.coreutils.CoreUtilsModule
import de.stefan.lang.coreutils.nativecontext.ContextProvider
import de.stefan.lang.foundationCore.api.assets.FileAsset
import de.stefan.lang.foundationCore.api.assets.FileAssetLoading
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.module.Module

@RunWith(AndroidJUnit4::class)
class FileAssetLoaderTest: CoreTest() {
    override val testModules: List<Module> = listOf(
        CoreModule.testModule,
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

    private fun createSUT(): FileAssetLoading {
        val loader = FileAssetLoader(
            appContextProvider = ContextProvider(context),
            logging = CoreUtilsModule.logger()
        )

        return loader
    }
}