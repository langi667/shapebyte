package de.stefan.lang.coreutils.api.progress
import kotlin.math.roundToInt
import kotlin.time.Duration

data class Progress(private val progress: Float) {
    companion object {
        val ZERO = Progress(0f)
        val COMPLETE = Progress(1f)

        const val ABSOLUTE: Int = 100

        // TODO: consider rounding rule
        fun with(current: Int, total: Int): Progress {
            val progress = current.toFloat() / total.toFloat()
            return Progress(progress)
        }

        // TODO: consider rounding rule
        // TODO: Test
        fun with(current: Float, total: Float): Progress {
            val progress = current / total
            return Progress(progress)
        }

        // TODO: consider rounding rule
        fun with(current: UInt, total: UInt): Progress {
            val progress = current.toFloat() / total.toFloat()
            return Progress(progress)
        }

        // TODO: consider rounding rule
        // TODO: Test
        fun with(current: Duration, total: Duration): Progress {
            val progress = current.inWholeMilliseconds.toFloat() /
                total.inWholeMilliseconds.toFloat()

            return Progress(progress)
        }
    }

    val value: Float = adjustProgress(progress)
    val absoluteValue: Int by lazy { (value * ABSOLUTE).roundToInt() }

    constructor(value: Int) : this(value.toFloat() / ABSOLUTE)

    // TODO: Test
    operator fun plus(progress: Progress): Progress = Progress(this.value + progress.value)

    private fun adjustProgress(value: Float): Float {
        return when {
            value < 0 -> 0f
            value > 1 -> 1f
            else -> value
        }
    }
}
