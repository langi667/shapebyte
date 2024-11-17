package de.stefan.lang.shapebyte.utils.device.devicesize


actual class DeviceSizeCategoryProvider actual constructor(
    actual override val screenSizeProvider: ScreenSizeProviding
): DeviceSizeCategoryProviding {
    actual override val screenSize: Size by lazy { screenSizeProvider.screenSize }
    actual override val sizeCategory: DeviceSizeCategory by lazy {
        deviceSizeCategoryForSize(this.screenSize)
    }

    actual override fun sizeCategories(): List<DeviceSizeCategory> = iPhoneSizeBounds
        .entries
        .map { it.category }
}