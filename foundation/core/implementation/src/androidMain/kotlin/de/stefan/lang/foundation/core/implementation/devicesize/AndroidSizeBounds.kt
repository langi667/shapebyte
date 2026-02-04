package de.stefan.lang.foundation.core.implementation.devicesize

import de.stefan.lang.foundation.core.contract.devicesize.DeviceSizeCategory
import de.stefan.lang.foundation.core.contract.devicesize.Size

public enum class AndroidSizeBounds(public val category: DeviceSizeCategory) {
    XSmall(DeviceSizeCategory.XSmall(size = Size(width = 240f, height = 480f))),
    Small(DeviceSizeCategory.Small(size = Size(width = 360f, height = 640f))),
    Medium(DeviceSizeCategory.Medium(size = Size(width = 480f, height = 800f))),
    Large(DeviceSizeCategory.Large(size = Size(width = 560f, height = 900f))),
    ;

    public val bounds: Size = category.size
    public val width: Float = bounds.width
    public val widthInt: Int = bounds.width.toInt()

    public val height: Float = bounds.height
    public val heightInt: Int = bounds.height.toInt()
}
