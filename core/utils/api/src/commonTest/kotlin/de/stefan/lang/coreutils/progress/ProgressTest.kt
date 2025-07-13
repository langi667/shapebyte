package de.stefan.lang.coreutils.progress

import de.stefan.lang.coreutils.api.progress.Progress
import kotlin.test.Test
import kotlin.test.assertEquals

class ProgressTest {
    @Test
    fun `value should be correctly adjusted`() {
        var sut = Progress(0)
        assertEquals(0f, sut.value)

        sut = Progress(-1f)
        assertEquals(0f, sut.value)

        sut = Progress(0.53f)
        assertEquals(0.53f, sut.value)

        sut = Progress(1f)
        assertEquals(1f, sut.value)

        sut = Progress(1.3f)
        assertEquals(1.0f, sut.value)
    }

    @Test
    fun `absoluteValue should be between 0 and 100`() {
        var sut = Progress(0)
        assertEquals(0, sut.absoluteValue)

        sut = Progress(-0.3f)
        assertEquals(0, sut.absoluteValue)

        sut = Progress(0.53f)
        assertEquals(53, sut.absoluteValue)

        sut = Progress(1f)
        assertEquals(100, sut.absoluteValue)

        sut = Progress(1.3f)
        assertEquals(100, sut.absoluteValue)
    }

    @Test
    fun `Defined values must be correct`() {
        assertEquals(0f, Progress.ZERO.value)
        assertEquals(1f, Progress.COMPLETE.value)
    }

    @Test
    fun `with should compute correct progress`() {
        assertEquals(Progress(0.0f), Progress.with(0, 100))
        assertEquals(Progress(0.5f), Progress.with(50, 100))
        assertEquals(Progress(0.75f), Progress.with(75, 100))

        assertEquals(Progress(1f), Progress.with(100, 100))
        assertEquals(Progress(1f).value, Progress.with(120, 100).value)
    }

    @Test
    fun `with unsigned integer should compute correct progress`() {
        assertEquals(Progress(0.0f), Progress.with(0u, 100u))
        assertEquals(Progress(0.5f), Progress.with(50u, 100u))
        assertEquals(Progress(0.75f), Progress.with(75u, 100u))

        assertEquals(Progress(1f), Progress.with(100u, 100u))
        assertEquals(Progress(1f).value, Progress.with(120u, 100u).value)
    }
}
