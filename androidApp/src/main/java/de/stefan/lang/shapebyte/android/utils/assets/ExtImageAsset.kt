package de.stefan.lang.shapebyte.android.utils.assets

import de.stefan.lang.foundation.core.contract.assets.ImageAsset

val ImageAsset.assetsPath: String
    get() = "file:///android_asset/$subPath"
