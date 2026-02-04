package de.stefan.lang.foundation.core.implementation.assets

import de.stefan.lang.utils.logging.contract.Loggable
import de.stefan.lang.utils.logging.contract.Logger
import de.stefan.lang.coreutils.contract.nativecontext.ContextProvider
import de.stefan.lang.foundation.core.contract.assets.FileAsset
import de.stefan.lang.foundation.core.contract.assets.FileAssetLoader
import kotlinx.cinterop.ExperimentalForeignApi
import platform.Foundation.NSBundle
import platform.Foundation.NSString
import platform.Foundation.NSUTF8StringEncoding
import platform.Foundation.stringWithContentsOfFile

public actual class FileAssetLoaderImpl actual constructor(
    appContextProvider: ContextProvider,
    logging: Logger
) :
    FileAssetLoader,
    Loggable
{
    public actual override val logger: Logger = logging

    @OptIn(ExperimentalForeignApi::class)
    public actual override fun loadFile(fileAsset: FileAsset): String {
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
