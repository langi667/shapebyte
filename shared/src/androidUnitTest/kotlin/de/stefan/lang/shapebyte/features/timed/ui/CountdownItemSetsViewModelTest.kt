package de.stefan.lang.shapebyte.features.timed.ui

import app.cash.turbine.test
import de.stefan.lang.shapebyte.di.DPI
import de.stefan.lang.shapebyte.features.workout.item.core.data.ItemSet
import de.stefan.lang.shapebyte.features.workout.item.timed.ui.CountdownItemSetsViewData
import de.stefan.lang.shapebyte.features.workout.item.timed.ui.CountdownItemSetsViewModel
import de.stefan.lang.shapebyte.shared.viewmodel.ui.UIState
import de.stefan.lang.shapebyte.utils.BaseCoroutineTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.time.Duration.Companion.minutes

class CountdownItemSetsViewModelTest : BaseCoroutineTest() {
    @Test
    fun `should have correct initial state`() {
        val sut = createSut()
        assertEquals(UIState.Data(CountdownItemSetsViewData()), sut.state.value)
    }

    // Deactivated, will be checked if the CountdownItemSets is needed
//    @Test
//    fun `start should emit correct states`() = test {
//        val sut = createSut()
//        assertEquals(UIState.Data(CountdownItemSetsViewData()), sut.state.value)
//
//        val items = listOf(
//            ItemSet.Timed.Seconds(1),
//            ItemSet.Timed.Seconds(1),
//            ItemSet.Timed.Seconds(1),
//            ItemSet.Timed.Seconds(1),
//            ItemSet.Timed.Seconds(1),
//        )
//        sut.state.test(timeout = 1.minutes) {
//            sut.start(items)
//            for (i in items.indices) {
//                // initial state, that is invoked when the current countdown tick/ item is about to
//                // be started. Should reset the UI
//                var expected = UIState.Data(
//                    data = CountdownItemSetsViewData(
//                        countdownText = "",
//                        scale = 1f,
//                        alpha = 1f,
//                        animationDuration = 0,
//                    ),
//                )
//
//                assertEquals(expected, awaitItem())
//
//                // Current countdown tick/ item is started started, setting target scale and alpha
//                expected = UIState.Data(
//                    data = CountdownItemSetsViewData(
//                        countdownText = (items.count() - i).toString(),
//                        scale = 3f,
//                        alpha = 0f,
//                        animationDuration = 900,
//                    ),
//                )
//
//                assertEquals(expected, awaitItem())
//            }
//
//            expectNoEvents()
//        }
//    }
//
//    @Test
//    fun `start should not emit any view states with content`() = test {
//        val sut = createSut()
//        assertEquals(UIState.Data(CountdownItemSetsViewData()), sut.state.value)
//
//        sut.start(emptyList())
//        sut.state.test {
//            val expected = UIState.Data(
//                data = CountdownItemSetsViewData(
//                    countdownText = "",
//                    scale = 1f,
//                    alpha = 1f,
//                    animationDuration = 0,
//                ),
//            )
//
//            assertEquals(expected, awaitItem())
//            expectNoEvents()
//        }
//    }

    private fun createSut(): CountdownItemSetsViewModel = DPI.countdownItemSetsViewModel()
}
