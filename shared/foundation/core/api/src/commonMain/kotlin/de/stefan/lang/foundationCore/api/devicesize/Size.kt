package de.stefan.lang.foundationCore.api.devicesize

data class Size(val width: Float, val height: Float) {
    companion object {
        val ZERO = Size(0f, 0f)
    }
}
