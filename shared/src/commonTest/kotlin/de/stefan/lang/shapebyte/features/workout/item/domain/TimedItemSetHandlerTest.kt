package de.stefan.lang.shapebyte.features.workout.item.domain

import app.cash.turbine.test
import de.stefan.lang.shapebyte.features.workout.item.data.ItemSet
import de.stefan.lang.shapebyte.features.workout.item.data.ItemSetState
import de.stefan.lang.shapebyte.features.workout.item.domain.timed.TimedItemSetHandler
import de.stefan.lang.shapebyte.utils.BaseCoroutineTest
import de.stefan.lang.shapebyte.utils.CountdownTimer
import de.stefan.lang.shapebyte.utils.mocks.SilentLogger
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs
import kotlin.time.Duration.Companion.seconds

class TimedItemSetHandlerTest : BaseCoroutineTest() {

    @Test
    fun `Test initial state`() = test {
        val sut = createSUT()
        assertEquals(ItemSetState.Idle, sut.stateFlow.value)
        assertEquals(1.seconds, sut.timerTick)
    }

    @Test
    fun `Test start`() = runTest {
        val sut = createSUT()

        val duration = 5
        val itemSet = ItemSet.Timed(duration)
        sut.start(itemSet, this)

        sut.stateFlow.test {
            awaitItem()
            for (i in 0..<duration) {
                val currState = awaitItem()

                if (i == 0) {
                    assertIs<ItemSetState.Started>(currState)
                } else {
                    assertIs<ItemSetState.Running>(currState)
                }
            }

            assertIs<ItemSetState.Finished>(awaitItem())
            cancel()
        }
    }

    @Test
    fun `Test start pause and resume`() = test {
        val sut = createSUT()

        val duration = 6
        val itemSet = ItemSet.Timed(duration)
        sut.start(itemSet, this)

        sut.stateFlow.test {
            awaitItem()
            for (i in 0..<duration / 2) {
                val currState = awaitItem()

                if (i == 0) {
                    assertIs<ItemSetState.Started>(currState)
                } else {
                    assertIs<ItemSetState.Running>(currState)
                }

                if (i == (duration / 2) - 1) {
                    sut.pause()
                    assertIs<ItemSetState.Paused>(awaitItem())
                    expectNoEvents()
                }
            }
        }

        sut.resume(this)
        sut.stateFlow.test {
            awaitItem()
            for (i in duration / 2..<duration) {
                val currState = awaitItem()
                assertIs<ItemSetState.Running>(currState)
            }
        }
    }

    private fun createSUT(): TimedItemSetHandler {
        val logger = SilentLogger()
        return TimedItemSetHandler(
            logger = logger,
            timer = CountdownTimer(logger),
        )
    }
}
