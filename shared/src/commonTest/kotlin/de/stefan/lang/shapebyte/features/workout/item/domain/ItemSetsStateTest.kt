package de.stefan.lang.shapebyte.features.workout.item.domain

import de.stefan.lang.shapebyte.features.workout.item.data.ItemSetData
import de.stefan.lang.shapebyte.utils.Progress
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue
import kotlin.time.Duration.Companion.seconds

class ItemSetsStateTest {
    @Test
    fun `isRunning should return true when running`() {
        assertTrue(ItemSetsState.Started(1).isRunning)
        assertTrue(
            ItemSetsState.Running.SetStarted(
                currentSet = 1,
                totalSets = 2,
                setData = ItemSetData.Timed(1.seconds, 2.seconds, Progress(0.5f), Progress(1f)),
            ).isRunning,
        )

        assertTrue(
            ItemSetsState.Running.SetRunning(
                currentSet = 1,
                totalSets = 2,
                currentSetProgress = Progress(0.5f),
                totalProgress = Progress(0.5f),
                setData = ItemSetData.Timed(1.seconds, 2.seconds, Progress(0.5f), Progress(1f)),
            ).isRunning,
        )

        assertTrue(
            ItemSetsState.Running.SetFinished(
                currentSet = 1,
                totalSets = 2,
                setData = ItemSetData.Timed(1.seconds, 2.seconds, Progress(0.5f), Progress(1f)),
            ).isRunning,
        )

        assertFalse(ItemSetsState.Idle.isRunning)
        assertFalse(ItemSetsState.Paused.isRunning)
        assertFalse(ItemSetsState.Finished.isRunning)
    }
}
