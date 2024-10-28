package de.stefan.lang.shapebyte.features.workout.item.data

import de.stefan.lang.shapebyte.utils.Progress
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue
import kotlin.time.Duration.Companion.seconds

class ItemSetStateTest {
    @Test
    fun `isStopped should return correct value`() {
        assertEquals(expected = true, actual = ItemSetState.Idle.isStopped)
        assertEquals(
            expected = true,
            actual = ItemSetState.Finished(
                ItemSetData.Timed(
                    timePassed = 1.seconds,
                    timeRemaining = 0.seconds,
                    progress = Progress.COMPLETE,
                    nextProgress = Progress.COMPLETE,
                ),
            ).isStopped,
        )

        assertEquals(
            expected = false,
            actual = ItemSetState.Started(
                ItemSetData.Timed(0.seconds, 0.seconds, Progress.ZERO, Progress.ZERO),
            ).isStopped,
        )

        assertEquals(
            expected = false,
            actual = ItemSetState.Running(
                ItemSetData.Timed(0.seconds, 0.seconds, Progress.ZERO, Progress.ZERO),
            ).isStopped,
        )

        assertEquals(
            expected = false,
            actual = ItemSetState.Paused(
                ItemSetData.Timed(0.seconds, 0.seconds, Progress.ZERO, Progress.ZERO),
            ).isStopped,
        )
    }

    @Test
    fun `isRunning should return correct value`() {
        assertTrue(
            ItemSetState.Started(
                ItemSetData.Timed(0.seconds, 0.seconds, Progress.ZERO, Progress.ZERO),
            ).isRunning,
        )

        assertTrue(
            ItemSetState.Running(
                ItemSetData.Timed(0.seconds, 0.seconds, Progress.ZERO, Progress.ZERO),
            ).isRunning,
        )

        assertFalse(
            ItemSetState.Paused(
                ItemSetData.Timed(0.seconds, 0.seconds, Progress.ZERO, Progress.ZERO),
            ).isRunning,
        )

        assertFalse(ItemSetState.Idle.isRunning)
        assertFalse(
            ItemSetState.Finished(
                ItemSetData.Timed(
                    timePassed = 1.seconds,
                    timeRemaining = 0.seconds,
                    progress = Progress.COMPLETE,
                    nextProgress = Progress.COMPLETE,
                ),
            ).isRunning,
        )
    }
}
