package de.stefan.lang.shapebyte.utils.assets

interface AssetLoading {
    fun loadFile(fileAsset: FileAsset): String

    /**
     * On Android, pass in the application context.
     * On iOS Pass the bundle
     */
    fun setup(context: Any?)
}
