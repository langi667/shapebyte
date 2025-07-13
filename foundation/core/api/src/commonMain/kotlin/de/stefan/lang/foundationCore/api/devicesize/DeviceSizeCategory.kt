package de.stefan.lang.foundationCore.api.devicesize

sealed interface DeviceSizeCategory {
    val size: Size

    data object None : DeviceSizeCategory {
        override val size: Size
            get() = Size.ZERO
    }

    data class XSmall(override val size: Size) : DeviceSizeCategory
    data class Small(override val size: Size) : DeviceSizeCategory
    data class Medium(override val size: Size) : DeviceSizeCategory
    data class Large(override val size: Size) : DeviceSizeCategory

    val isXSmall: Boolean get() = this is XSmall
    val isSmall: Boolean get() = this is Small
    val isMedium: Boolean get() = this is Medium
    val isLarge: Boolean get() = this is Large
}
