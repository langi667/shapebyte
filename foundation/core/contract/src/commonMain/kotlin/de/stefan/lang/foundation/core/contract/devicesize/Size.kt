package de.stefan.lang.foundation.core.contract.devicesize

public data class Size public constructor(public val width: Float, public val height: Float) {
    public companion object {
        public val ZERO: Size = Size(0f, 0f)
    }
}
