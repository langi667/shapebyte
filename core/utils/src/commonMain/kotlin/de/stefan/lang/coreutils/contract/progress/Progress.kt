package de.stefan.lang.coreutils.contract.progress

import kotlin.math.roundToInt
import kotlin.time.Duration

public data class Progress(private val progress: Float) {
    public companion object {
        public val ZERO: Progress = Progress(0f)
        public val COMPLETE: Progress = Progress(1f)

        public const val ABSOLUTE: Int = 100

        public fun with(current: Int, total: Int): Progress {
            val progress = current.toFloat() / total.toFloat()
            return Progress(progress)
        }

        public fun with(current: Float, total: Float): Progress {
            val progress = current / total
            return Progress(progress)
        }

        public fun with(current: UInt, total: UInt): Progress {
            val progress = current.toFloat() / total.toFloat()
            return Progress(progress)
        }

        public fun with(current: Duration, total: Duration): Progress {
            val progress = current.inWholeMilliseconds.toFloat() /
                total.inWholeMilliseconds.toFloat()

            return Progress(progress)
        }
    }

    public val value: Float = adjustProgress(progress)
    public val absoluteValue: Int by lazy { (value * ABSOLUTE).roundToInt() }

    public constructor(value: Int) : this(value.toFloat() / ABSOLUTE)

    public operator fun plus(progress: Progress): Progress = Progress(this.value + progress.value)

    private fun adjustProgress(value: Float): Float {
        return when {
            value < 0 -> 0f
            value > 1 -> 1f
            else -> value
        }
    }
}
