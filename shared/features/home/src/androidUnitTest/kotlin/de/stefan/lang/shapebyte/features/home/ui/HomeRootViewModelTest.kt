package de.stefan.lang.shapebyte.features.home.ui

import app.cash.turbine.test
import de.stefan.lang.featureToggles.api.FeatureToggle
import de.stefan.lang.featureToggles.api.FeatureToggleLoading
import de.stefan.lang.featureToggles.api.FeatureToggleState
import de.stefan.lang.foundationCore.api.loadstate.LoadState
import de.stefan.lang.foundationUi.api.viewmodel.UIState
import de.stefan.lang.featureToggles.api.FeatureId
import de.stefan.lang.shapebyte.features.home.BaseHomeFeatureTest
import de.stefan.lang.shapebyte.features.home.HomeRootViewData
import de.stefan.lang.shapebyte.features.home.HomeRootViewModel
import de.stefan.lang.shapebyte.features.workout.workoutDomain.workout.FetchRecentWorkoutHistoryUseCase
import de.stefan.lang.shapebyte.features.workout.workoutDomain.workout.QuickWorkoutsUseCase
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import org.koin.core.component.get
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class HomeRootViewModelTest : BaseHomeFeatureTest() {
    private val featureToggleLoading: FeatureToggleLoading = mockk(relaxed = true)

    @Test
    fun `initial state`() = test {
        val sut = createSUT()
        assertEquals(UIState.Idle, sut.state.value)
    }

    @Test
    fun `update should update state`() = test {
        val sut = createSUT()
        sut.update()

        sut.state.test {
            assertEquals(UIState.Loading, awaitItem())
            val data: HomeRootViewData? = awaitItem().dataOrNull()
            assertNotNull(data)

            assertFalse(data.quickWorkouts.isEmpty())
            assertFalse(data.recentHistory.isEmpty())

            expectNoEvents()
        }
    }


    @Test
    fun `state should be empty if feature toggles are disabled `() = test {
        val sut = createSUT(
            recentHistoryState = FeatureToggleState.DISABLED,
            quickWorkoutsState = FeatureToggleState.DISABLED
        )

        sut.update()

        sut.state.test {
            assertEquals(UIState.Loading, awaitItem())
            val data: HomeRootViewData? = awaitItem().dataOrNull()
            assertNotNull(data)

            assertTrue(data.quickWorkouts.isEmpty())
            assertTrue(data.recentHistory.isEmpty())

            expectNoEvents()
        }
    }

    @Test
    fun `state contains no history if feature is disabled`() = test {
        val sut = createSUT(
            recentHistoryState = FeatureToggleState.DISABLED,
            quickWorkoutsState = FeatureToggleState.ENABLED
        )
        sut.update()

        sut.state.test {
            assertEquals(UIState.Loading, awaitItem())
            val data: HomeRootViewData? = awaitItem().dataOrNull()
            assertNotNull(data)

            assertFalse(data.quickWorkouts.isEmpty())
            assertTrue(data.recentHistory.isEmpty())

            expectNoEvents()
        }
    }


    @Test
    fun `state contains no quick workouts if feature is disabled`() = test {
        val sut = createSUT(
            recentHistoryState = FeatureToggleState.ENABLED,
            quickWorkoutsState = FeatureToggleState.DISABLED
        )
        sut.update()

        sut.state.test {
            assertEquals(UIState.Loading, awaitItem())
            val data: HomeRootViewData? = awaitItem().dataOrNull()
            assertNotNull(data)

            assertTrue(data.quickWorkouts.isEmpty())
            assertFalse(data.recentHistory.isEmpty())

            expectNoEvents()
        }
    }

    private fun createSUT(
        recentHistoryState: FeatureToggleState = FeatureToggleState.ENABLED,
        quickWorkoutsState: FeatureToggleState = FeatureToggleState.ENABLED,
    ): HomeRootViewModel {

        val recentHistoryFT = FeatureToggle(FeatureId.RECENT_HISTORY.name, recentHistoryState)
        val quickWorkoutsFT = FeatureToggle(FeatureId.QUICK_WORKOUTS.name, quickWorkoutsState)

        every { featureToggleLoading.invoke(FeatureId.RECENT_HISTORY.name) } returns flowOf(
            LoadState.Success(recentHistoryFT),
        )
        every { featureToggleLoading.invoke(FeatureId.QUICK_WORKOUTS.name) } returns flowOf(
            LoadState.Success(quickWorkoutsFT),
        )

        return HomeRootViewModel(
            navigationHandler = mockk(relaxed = true),
            currentWorkoutScheduleEntryUseCase = get(),
            recentHistoryUseCase = FetchRecentWorkoutHistoryUseCase(
                repository = get(),
                logger = get(),
                coroutineContextProviding = get(),
                coroutineScopeProviding = get(),
                featureToggleLoading = featureToggleLoading,
            ),
            quickWorkoutsUseCase = QuickWorkoutsUseCase(
                repository = get(),
                logger = get(),
                featureToggleLoading = featureToggleLoading,
                scopeProvider = get(),
                dispatcherProvider = get(),
            ),
            navigationRequestBuilder = get(),
            logger = get(),
            coroutineContextProvider = get(),
        )
    }
}
