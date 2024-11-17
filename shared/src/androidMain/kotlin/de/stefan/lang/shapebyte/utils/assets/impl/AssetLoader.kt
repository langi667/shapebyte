package de.stefan.lang.shapebyte.utils.assets.impl

import android.content.Context
import de.stefan.lang.shapebyte.utils.assets.AssetLoading
import de.stefan.lang.shapebyte.utils.assets.FileAsset
import de.stefan.lang.shapebyte.utils.logging.Loggable
import de.stefan.lang.shapebyte.utils.logging.Logging

actual class AssetLoader actual constructor(logging: Logging) :
    AssetLoading,
    Loggable {
    actual override val logger = logging
    private lateinit var context: Context

    actual override fun setup(context: Any?) {
        this.context = context as Context
    }

    actual override fun loadFile(fileAsset: FileAsset): String {
        return context.assets.open(fileAsset.subPath).bufferedReader().use { it.readText() }
    }
}
