package de.stefan.lang.shapebyte.utils.assets.impl

import de.stefan.lang.shapebyte.utils.assets.FileAsset
import de.stefan.lang.shapebyte.utils.assets.FileAssetLoading
import de.stefan.lang.shapebyte.utils.logging.Loggable
import de.stefan.lang.shapebyte.utils.logging.Logging

expect class FileAssetLoader(logging: Logging) :
    FileAssetLoading,
    Loggable {
    override val logger: Logging

    /**
     * On Android, pass in the application context.
     * On iOS Pass the bundle
     */
    override fun setup(context: Any?)

    override fun loadFile(fileAsset: FileAsset): String
}
