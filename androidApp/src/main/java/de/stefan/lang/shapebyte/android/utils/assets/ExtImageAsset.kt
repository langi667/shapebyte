package de.stefan.lang.shapebyte.android.utils.assets

import de.stefan.lang.foundationCore.api.assets.ImageAsset

val ImageAsset.assetsPath: String
    get() = "file:///android_asset/$subPath"
