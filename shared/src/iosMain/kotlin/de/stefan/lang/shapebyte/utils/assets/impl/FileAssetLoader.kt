package de.stefan.lang.shapebyte.utils.assets.impl

import de.stefan.lang.shapebyte.utils.assets.FileAsset
import de.stefan.lang.shapebyte.utils.assets.FileAssetLoading
import de.stefan.lang.shapebyte.utils.logging.Loggable
import de.stefan.lang.shapebyte.utils.logging.Logging
import kotlinx.cinterop.ExperimentalForeignApi
import platform.Foundation.NSBundle
import platform.Foundation.NSString
import platform.Foundation.NSUTF8StringEncoding
import platform.Foundation.stringWithContentsOfFile

actual class FileAssetLoader actual constructor(logging: Logging) :
    FileAssetLoading,
    Loggable
{
    actual override val logger = logging
    private lateinit var bundle: NSBundle

    actual override fun setup(context: Any?) {
        bundle = context as NSBundle
    }

    @OptIn(ExperimentalForeignApi::class)
    actual override fun loadFile(fileAsset: FileAsset): String {
        val path = bundle.pathForResource(
            name = fileAsset.fileNameWithoutEnding,
            ofType = fileAsset.fileEnding
        ) ?: kotlin.run {
            logE("File not found: ${fileAsset.assetName}")
            return ""
        }

        val contents = NSString.stringWithContentsOfFile(
            path = path, encoding = NSUTF8StringEncoding,
            error = null
        )

        if (contents == null) {
            logE("Could not read file: ${fileAsset.assetName} at path: $path")
            return ""
        } else {
            logD("Read file: ${fileAsset.assetName} from path: $path with content: $contents")
            return contents
        }
    }
}