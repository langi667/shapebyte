package de.stefan.lang.shapebyte.features.workout.ui

import app.cash.turbine.test
import de.stefan.lang.shapebyte.features.workout.data.ItemSet
import de.stefan.lang.shapebyte.features.workout.data.None
import de.stefan.lang.shapebyte.features.workout.domain.ItemSetsHandler
import de.stefan.lang.shapebyte.shared.viewmodel.ui.UIState
import de.stefan.lang.shapebyte.utils.BaseCoroutineTest
import de.stefan.lang.shapebyte.utils.logging.Logging
import org.koin.test.inject
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.time.Duration.Companion.seconds

class CountdownItemSetsViewModelTest : BaseCoroutineTest() {
    private val itemSetsHandler by inject<ItemSetsHandler>()
    private val logger by inject<Logging>()

    @Test
    fun `should have correct initial state`() {
        val sut = createSut()
        assertEquals(UIState.Data(CountdownItemSetsViewData()), sut.state.value)
    }

    @Test
    fun `start should emit correct states`() = test {
        val sut = createSut()
        assertEquals(UIState.Data(CountdownItemSetsViewData()), sut.state.value)

        val items = listOf(
            ItemSet.Timed(1.seconds, None),
            ItemSet.Timed(1.seconds, None),
            ItemSet.Timed(1.seconds, None),
            ItemSet.Timed(1.seconds, None),
            ItemSet.Timed(1.seconds, None),
        )

        sut.start(items)
        sut.state.test {
            for (i in items.indices) {
                // initial state, that is invoked when the current countdown tick/ item is about to
                // be started. Should reset the UI
                var expected = UIState.Data(
                    data = CountdownItemSetsViewData(
                        countdownText = "",
                        scale = 1f,
                        alpha = 1f,
                        animationDuration = 0,
                    ),
                )

                assertEquals(expected, awaitItem())

                // Current countdown tick/ item is started started, setting target scale and alpha
                expected = UIState.Data(
                    data = CountdownItemSetsViewData(
                        countdownText = (items.count() - i).toString(),
                        scale = 3f,
                        alpha = 0f,
                        animationDuration = 900,
                    ),
                )

                assertEquals(expected, awaitItem())
            }

            expectNoEvents()
        }
    }

    @Test
    fun `start should not emit any view states with content`() = test {
        val sut = createSut()
        assertEquals(UIState.Data(CountdownItemSetsViewData()), sut.state.value)

        sut.start(emptyList())
        sut.state.test {
            val expected = UIState.Data(
                data = CountdownItemSetsViewData(
                    countdownText = "",
                    scale = 1f,
                    alpha = 1f,
                    animationDuration = 0,
                ),
            )

            assertEquals(expected, awaitItem())
            expectNoEvents()
        }
    }

    private fun createSut(): CountdownItemSetsViewModel {
        return CountdownItemSetsViewModel(itemSetsHandler, logger)
    }
}
