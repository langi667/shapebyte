package de.stefan.lang.shapebyte.utils

import app.cash.turbine.test
import de.stefan.lang.shapebyte.utils.mocks.MockLogger
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.time.Duration.Companion.seconds

class CountdownTimerTest : BaseCoroutineTest() {

    @Test
    fun `Initial State must be Idle`() {
        val timer = createSUT()
        assertEquals(timer.state, CountdownTimer.State.Idle)
    }

    @Test
    fun `Should emit correct states when started`() = test {
        val duration = 5
        val interval = 1.seconds
        val timer = createSUT()

        assertEquals(timer.state, CountdownTimer.State.Idle)

        timer.setup(interval = interval, duration = duration.seconds)
        timer.start(this).test {
            for (i in 0..duration) {
                assertEquals(
                    CountdownTimer.State.Running(i.seconds, duration.seconds, interval),
                    awaitItem(),
                )
            }

            assertEquals(
                CountdownTimer.State.Stopped(duration.seconds, duration.seconds),
                awaitItem(),
            )

            expectNoEvents()
        }
    }

    @Test
    fun `pause should pause timer`() = runTest {
        val duration = 5
        val interval = 1.seconds
        val timer = createSUT()

        assertEquals(timer.state, CountdownTimer.State.Idle)

        timer.setup(
            duration = duration.seconds,
            interval = interval,
        )

        timer.start(scope = this).test {
            for (i in 0..2) {
                assertEquals(
                    CountdownTimer.State.Running(i.seconds, duration.seconds, interval),
                    awaitItem(),
                )
            }

            timer.pause()
            assertEquals(
                CountdownTimer.State.Paused(2.seconds, duration.seconds, interval),
                awaitItem(),
            )
            expectNoEvents()
        }
    }

    @Test
    fun `start on paused timer should resume`() = runTest {
        val duration = 5
        val interval = 1.seconds
        val timer = createSUT()

        assertEquals(timer.state, CountdownTimer.State.Idle)

        timer.setup(
            duration = duration.seconds,
            interval = interval,
        )

        timer.start(scope = this).test {
            for (i in 0..2) {
                assertEquals(
                    CountdownTimer.State.Running(i.seconds, duration.seconds, interval),
                    awaitItem(),
                )
            }

            timer.pause()
            assertEquals(
                CountdownTimer.State.Paused(2.seconds, duration.seconds, interval),
                awaitItem(),
            )
            expectNoEvents()
        }

        timer.start(this).test {
            for (i in 2..duration) {
                assertEquals(
                    CountdownTimer.State.Running(i.seconds, duration.seconds, interval),
                    awaitItem(),
                )
            }

            assertEquals(
                CountdownTimer.State.Stopped(duration.seconds, duration.seconds),
                awaitItem(),
            )

            expectNoEvents()
        }
    }

    @Test
    fun `stop should stop timer`() = runTest {
        val duration = 5
        val interval = 1.seconds
        val timer = createSUT()

        assertEquals(timer.state, CountdownTimer.State.Idle)

        timer.setup(
            duration = duration.seconds,
            interval = interval,
        )

        timer.start(
            scope = this,
        ).test {
            for (i in 0..2) {
                assertEquals(
                    CountdownTimer.State.Running(i.seconds, duration.seconds, interval),
                    awaitItem(),
                )
            }

            timer.stop()
            assertEquals(
                CountdownTimer.State.Stopped(2.seconds, duration.seconds),
                awaitItem(),
            )
            expectNoEvents()
        }
    }

    @Test
    fun `start should do nothing if no setup was called before`() = runTest {
        val timer = createSUT()

        timer.start(this).test {
            assertEquals(CountdownTimer.State.Idle, awaitItem())
            expectNoEvents()
        }
    }

    @Test
    fun `start should be reusable once stopped`() = runTest {
        val timer = createSUT()
        val duration = 5

        timer.setup(duration = duration.seconds, interval = 1.seconds)

        repeat(10) {
            timer.start(this).test {
                for (i in 0..duration) {
                    assertEquals(
                        CountdownTimer.State.Running(
                            elapsed = i.seconds,
                            duration = duration.seconds,
                            interval = 1.seconds,
                        ),
                        actual = awaitItem(),
                    )
                }

                assertEquals(
                    expected = CountdownTimer.State.Stopped(elapsed = duration.seconds, duration = duration.seconds),
                    actual = awaitItem(),
                )
            }
        }
    }

    @Test
    fun `Timer should be reusable if setting up again`() = runTest {
        val duration = 5
        val interval = 1.seconds
        val timer = createSUT()

        assertEquals(timer.state, CountdownTimer.State.Idle)

        // launching timer multiple times
        repeat(3) {
            timer.setup(interval = interval, duration = duration.seconds)
            timer.start(scope = this).test {
                for (i in 0..duration) {
                    val msg = "Loop $it, Duration: $i"
                    println(msg)
                    assertEquals(
                        expected = CountdownTimer.State.Running(i.seconds, duration.seconds, interval),
                        actual = awaitItem(),
                    )
                }

                assertEquals(
                    expected = CountdownTimer.State.Stopped(duration.seconds, duration.seconds),
                    actual = awaitItem(),
                )

                expectNoEvents()
            }
        }
    }

    @Test
    fun `Timer should be reusable when paused`() = runTest {
        val duration = 5
        val interval = 1.seconds
        val timer = createSUT()

        timer.setup(
            duration = duration.seconds,
            interval = interval,
        )
        timer.start(scope = this).test {
            for (i in 0..2) {
                assertEquals(
                    CountdownTimer.State.Running(i.seconds, duration.seconds, interval),
                    awaitItem(),
                )
            }

            timer.pause()
            assertEquals(
                CountdownTimer.State.Paused(2.seconds, duration.seconds, interval),
                awaitItem(),
            )

            timer.setup(duration.seconds, interval)
            timer.start(this).test {
                for (i in 0..duration) {
                    assertEquals(
                        CountdownTimer.State.Running(i.seconds, duration.seconds, interval),
                        awaitItem(),
                    )
                }

                assertEquals(
                    CountdownTimer.State.Stopped(duration.seconds, duration.seconds),
                    awaitItem(),
                )

                expectNoEvents()
            }

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `TimerState Stopped returns correct value for isFinished`() {
        var sut = CountdownTimer.State.Stopped(elapsed = 1.seconds, duration = 2.seconds)
        assertEquals(false, sut.isFinished)

        sut = CountdownTimer.State.Stopped(elapsed = 2.seconds, duration = 2.seconds)
        assertEquals(true, sut.isFinished)
    }

    @Test
    fun `setup should stop timer and reset`() = runTest {
        val duration = 6
        val durationBreak = duration / 2
        val newDuration = 12

        val interval = 1.seconds
        val timer = createSUT()

        timer.setup(
            duration = duration.seconds,
            interval = interval,
        )

        timer.start(
            scope = this,
        ).test {
            for (i in 0..durationBreak) {
                assertEquals(
                    CountdownTimer.State.Running(i.seconds, duration.seconds, interval),
                    awaitItem(),
                )

                if (i == durationBreak) {
                    timer.setup( // should stop timer
                        duration = newDuration.seconds,
                        interval = interval,
                    )

                    assertEquals( // check if timer is stopped
                        CountdownTimer.State.Stopped(durationBreak.seconds, duration.seconds),
                        awaitItem(),
                    )
                }
            }

            expectNoEvents()
        }

        // resume timer with new duration that was passed using setup before
        timer.start(
            scope = this,
        ).test {
            for (i in 0..newDuration) {
                assertEquals(
                    CountdownTimer.State.Running(i.seconds, newDuration.seconds, interval),
                    awaitItem(),
                )
            }

            assertEquals( // check if timer is stopped
                CountdownTimer.State.Stopped(newDuration.seconds, newDuration.seconds),
                awaitItem(),
            )
        }
    }

    @Test
    fun `Launch should setup and start timer correctly`() = test {
        val duration = 10
        val interval = 2
        val timer = createSUT()

        assertEquals(timer.state, CountdownTimer.State.Idle)

        timer.launch(interval = interval.seconds, duration = duration.seconds, scope = this).test {
            for (i in 0..(duration / interval)) {
                assertEquals(
                    CountdownTimer.State.Running(
                        elapsed = i.seconds * interval,
                        duration = duration.seconds,
                        interval = interval.seconds,
                    ),
                    awaitItem(),
                )
            }

            assertEquals(
                CountdownTimer.State.Stopped(duration.seconds, duration.seconds),
                awaitItem(),
            )

            expectNoEvents()
        }
    }
    
    private fun createSUT(): CountdownTimer = CountdownTimer(MockLogger())
}
