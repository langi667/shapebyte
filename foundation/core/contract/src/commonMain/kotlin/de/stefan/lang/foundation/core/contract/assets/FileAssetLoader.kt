package de.stefan.lang.foundation.core.contract.assets

public interface FileAssetLoader {
    public fun loadFile(fileAsset: FileAsset): String
}
