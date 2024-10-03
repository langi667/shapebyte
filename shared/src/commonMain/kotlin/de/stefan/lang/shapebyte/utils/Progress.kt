package de.stefan.lang.shapebyte.utils
import kotlin.math.roundToInt

data class Progress(private val progress: Float) {
    companion object {
        val ZERO = Progress(0f)
        val COMPLETE = Progress(1f)

        private const val ABSOLUTE: Int = 100
    }

    val value: Float = adjustProgress(progress)
    val absoluteValue: Int by lazy { (value * ABSOLUTE).roundToInt() }

    constructor(value: Int) : this(value.toFloat() / ABSOLUTE)

    private fun adjustProgress(value: Float): Float {
        return when {
            value < 0 -> 0f
            value > 1 -> 1f
            else -> value
        }
    }
}
