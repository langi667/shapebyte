package de.stefan.lang.foundationCore.impl.devicesize

import de.stefan.lang.foundation.core.contract.devicesize.DeviceSizeCategory
import de.stefan.lang.foundation.core.contract.devicesize.Size

enum class AndroidSizeBounds(val category: DeviceSizeCategory) {
    XSmall(DeviceSizeCategory.XSmall(size = Size(width = 240f, height = 480f))),
    Small(DeviceSizeCategory.Small(size = Size(width = 360f, height = 640f))),
    Medium(DeviceSizeCategory.Medium(size = Size(width = 480f, height = 800f))),
    Large(DeviceSizeCategory.Large(size = Size(width = 560f, height = 900f))),
    ;

    val bounds = category.size
    val width = bounds.width
    val widthInt = bounds.width.toInt()

    val height = bounds.height
    val heightInt = bounds.height.toInt()
}
