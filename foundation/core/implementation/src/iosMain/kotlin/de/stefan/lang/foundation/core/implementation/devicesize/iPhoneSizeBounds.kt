package de.stefan.lang.foundation.core.implementation.devicesize

import de.stefan.lang.foundation.core.contract.devicesize.DeviceSizeCategory
import de.stefan.lang.foundation.core.contract.devicesize.Size

public enum class iPhoneSizeBounds(public val category: DeviceSizeCategory) {
    xSmall(DeviceSizeCategory.XSmall(size = Size(width = 320f, height = 568f))),
    small(DeviceSizeCategory.Small(size = Size(width = 375f, height = 667f))),
    medium(DeviceSizeCategory.Medium(size = Size(width = 390f, height = 844f))),
    large(DeviceSizeCategory.Large(size = Size(width = 430f, height = 932f))),
    // TODO: xLarge
    // https://www.appmysite.com/blog/the-complete-guide-to-iphone-screen-resolutions-and-sizes/
    ;


    public val bounds: Size = category.size
    public val width: Float = bounds.width
    public val widthInt: Int = bounds.width.toInt()

    public val height: Float = bounds.height
    public val heightInt: Int = bounds.height.toInt()

    public val widthCG: Double = width.toDouble()
    public val heightCG: Double = height.toDouble()

}
