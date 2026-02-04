package de.stefan.lang.foundation.core.contract.devicesize

public sealed interface DeviceSizeCategory {
    public val size: Size

    public data object None : DeviceSizeCategory {
        override val size: Size
            get() = Size.ZERO
    }

    public data class XSmall(override val size: Size) : DeviceSizeCategory
    public data class Small(override val size: Size) : DeviceSizeCategory
    public data class Medium(override val size: Size) : DeviceSizeCategory
    public data class Large(override val size: Size) : DeviceSizeCategory

    public val isXSmall: Boolean get() = this is XSmall
    public val isSmall: Boolean get() = this is Small
    public val isMedium: Boolean get() = this is Medium
    public val isLarge: Boolean get() = this is Large
}
