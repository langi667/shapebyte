package de.stefan.lang.foundationCore.impl.assets

import de.stefan.lang.coreutils.contract.logging.Loggable
import de.stefan.lang.coreutils.contract.logging.Logging
import de.stefan.lang.coreutils.contract.nativecontext.ContextProvider
import de.stefan.lang.foundation.core.contract.assets.FileAsset
import de.stefan.lang.foundation.core.contract.assets.FileAssetLoading
import kotlinx.cinterop.ExperimentalForeignApi
import platform.Foundation.NSBundle
import platform.Foundation.NSString
import platform.Foundation.NSUTF8StringEncoding
import platform.Foundation.stringWithContentsOfFile

actual class FileAssetLoader actual constructor(
    appContextProvider: ContextProvider,
    logging: Logging
) :
    FileAssetLoading,
    Loggable
{
    actual override val logger = logging

    @OptIn(ExperimentalForeignApi::class)
    actual override fun loadFile(fileAsset: FileAsset): String {
        val path = NSBundle.mainBundle.pathForResource(
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