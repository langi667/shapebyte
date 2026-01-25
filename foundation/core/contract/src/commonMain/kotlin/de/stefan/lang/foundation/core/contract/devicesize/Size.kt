package de.stefan.lang.foundation.core.contract.devicesize

data class Size(val width: Float, val height: Float) {
    companion object {
        val ZERO = Size(0f, 0f)
    }
}
