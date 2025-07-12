package de.stefan.lang.shapebyte.features.home.api

import app.cash.turbine.test
import de.stefan.lang.featureToggles.api.FeatureToggle
import de.stefan.lang.featureToggles.api.LoadFeatureToggleUseCase
import de.stefan.lang.featureToggles.api.FeatureToggleState
import de.stefan.lang.foundationCore.api.loadstate.LoadState
import de.stefan.lang.foundationUi.api.state.UIState
import de.stefan.lang.featureToggles.api.FeatureId
import de.stefan.lang.shapebyte.features.home.HomeModule
import de.stefan.lang.shapebyte.features.workout.workoutDomain.workout.FetchRecentWorkoutHistoryUseCase
import de.stefan.lang.shapebyte.features.workout.workoutDomain.workout.QuickWorkoutsUseCase
import de.stefan.lang.shapebyte.featuretest.FeatureTest
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import org.koin.core.component.get
import org.koin.core.module.Module
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class HomeRootViewModelTest : FeatureTest() {
    private val loadFeatureToggleUseCase: LoadFeatureToggleUseCase = mockk(relaxed = true)

    override val testModules: List<Module>
        get() = super.testModules + HomeModule.testModules

    @Test
    fun `initial state`() = test {
        val sut = createSUT()
        assertEquals(UIState.Idle, sut.state.value)
    }

    @Test
    fun `update should update state`() = test {
        val sut = createSUT()
        sut.intent(HomeRootUIIntent.Update)

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

        sut.intent(HomeRootUIIntent.Update)

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
        sut.intent(HomeRootUIIntent.Update)

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

        sut.intent(HomeRootUIIntent.Update)

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

        every { loadFeatureToggleUseCase.invoke(FeatureId.RECENT_HISTORY.name) } returns flowOf(
            LoadState.Success(recentHistoryFT),
        )
        every { loadFeatureToggleUseCase.invoke(FeatureId.QUICK_WORKOUTS.name) } returns flowOf(
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
                loadFeatureToggleUseCase = loadFeatureToggleUseCase,
            ),
            quickWorkoutsUseCase = QuickWorkoutsUseCase(
                repository = get(),
                logger = get(),
                loadFeatureToggleUseCase = loadFeatureToggleUseCase,
                scopeProvider = get(),
                dispatcherProvider = get(),
            ),
            navigationRequestBuilder = get(),
            logger = get(),
            coroutineContextProvider = get(),
        )
    }
}
