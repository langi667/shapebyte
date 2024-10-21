package de.stefan.lang.shapebyte.utils.device.devicesize

enum class iPhoneSizeBounds(val category: DeviceSizeCategory) {
    xSmall(DeviceSizeCategory.XSmall(size = Size(width = 320f, height = 568f))),
    small(DeviceSizeCategory.Small(size = Size(width = 375f, height = 667f))),
    medium(DeviceSizeCategory.Medium(size = Size(width = 390f, height = 844f))),
    large(DeviceSizeCategory.Large(size = Size(width = 430f, height = 932f))),
    ;

    val bounds = category.size
    val width = bounds.width
    val widthInt = bounds.width.toInt()

    val height = bounds.height
    val heightInt = bounds.height.toInt()

}