package de.stefan.lang.shapebyte.features.home.ui

import app.cash.turbine.test
import de.stefan.lang.shapebyte.featureToggles.FeatureTogglesModule
import de.stefan.lang.shapebyte.featureToggles.data.FeatureToggleState
import de.stefan.lang.shapebyte.featureToggles.data.FeatureToggle
import de.stefan.lang.shapebyte.featureToggles.data.FeatureToggleDatasource
import de.stefan.lang.shapebyte.featureToggles.data.impl.FeatureToggleDatasourceMock
import de.stefan.lang.shapebyte.SharedModule
import de.stefan.lang.shapebyte.featureToggles.FeatureId

import de.stefan.lang.foundationUI.viewmodel.UIState
import de.stefan.lang.shapebyte.features.home.BaseHomeFeatureTest
import de.stefan.lang.shapebyte.features.home.di.HomeModule

import org.koin.core.component.get
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class HomeRootViewModelTest : BaseHomeFeatureTest() {
    // TODO: use mockk instead of manual mock
    private val featureToggleDatasourceMock: FeatureToggleDatasourceMock
        get() = FeatureTogglesModule.get<FeatureToggleDatasource>() as FeatureToggleDatasourceMock

    @Test
    fun `initial state`() = test {
        val sut = createSUT()
        assertEquals(UIState.Idle, sut.state.value)
    }

    @Test
    fun `update should update state`() = test {
        featureToggleDatasourceMock.setFeatureToggles(
            listOf(
                FeatureToggle(FeatureId.RECENT_HISTORY.name, FeatureToggleState.ENABLED),
                FeatureToggle(FeatureId.QUICK_WORKOUTS.name, FeatureToggleState.ENABLED),
            ),
        )

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
        featureToggleDatasourceMock.setFeatureToggles(
            listOf(
                FeatureToggle(FeatureId.RECENT_HISTORY.name, FeatureToggleState.DISABLED),
                FeatureToggle(FeatureId.QUICK_WORKOUTS.name, FeatureToggleState.DISABLED),
            ),
        )

        val sut = createSUT()
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
        featureToggleDatasourceMock.setFeatureToggles(
            listOf(
                FeatureToggle(FeatureId.RECENT_HISTORY.name, FeatureToggleState.DISABLED),
                FeatureToggle(FeatureId.QUICK_WORKOUTS.name, FeatureToggleState.ENABLED),
            ),
        )

        val sut = createSUT()
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
        featureToggleDatasourceMock.setFeatureToggles(
            listOf(
                FeatureToggle(FeatureId.RECENT_HISTORY.name, FeatureToggleState.ENABLED),
                FeatureToggle(FeatureId.QUICK_WORKOUTS.name, FeatureToggleState.DISABLED),
            ),
        )

        val sut = createSUT()
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

    private fun createSUT(): HomeRootViewModel {
        return HomeModule.homeRootViewModel()
    }
}
