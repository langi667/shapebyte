package de.stefan.lang.shapebyte.featureTogglesDomain

import app.cash.turbine.test
import de.stefan.lang.featureToggles.api.FeatureToggle
import de.stefan.lang.featureToggles.api.FeatureToggleState
import de.stefan.lang.foundationCore.api.loadstate.LoadState
import de.stefan.lang.foundationCore.api.loadstate.asResultFlow
import de.stefan.lang.shapebyte.featureTogglesData.FeatureToggleDatasource
import de.stefan.lang.shapebyte.featureTogglesData.FeatureToggleRepository
import de.stefan.lang.shapebyte.featureTogglesData.impl.FeatureToggleError
import de.stefan.lang.shapebyte.featureTogglesDomain.impl.LoadFeatureToggleUseCase
import io.mockk.coEvery
import io.mockk.mockk
import org.koin.core.component.get
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class FeatureToggleUseCaseTest : BaseFeatureToggleDomainTest() {
    private val toggleId = "ToggleID"
    private val toggle = FeatureToggle(toggleId, FeatureToggleState.ENABLED)

    private val datasource: FeatureToggleDatasource = mockk(relaxed = true) {
        coEvery { fetchFeatureToggle(any()) } returns LoadState.Error(FeatureToggleError.NotFound(""))
        coEvery { fetchFeatureToggle(toggleId) } returns LoadState.Success(toggle)

    }

    private val sut: LoadFeatureToggleUseCase
        get() = LoadFeatureToggleUseCase(
            repository = FeatureToggleRepository(
                logger = get(),
                dataSource = datasource,
            ),
            logger = get(),
            coroutineScopeProviding = get(),
            coroutineContextProviding = get(),
        )

    @Test
    fun `should emit Success with null if toggle is not present`() = test {
        sut.invoke("some").asResultFlow().test {
            val item = awaitItem()
            assertNull(item.dataOrNull())
            expectNoEvents()
        }
    }

    @Test
    fun `should emit Success with FeatureToggle if toggle is present`() = test {
        sut.invoke(toggleId).asResultFlow().test {
            val item = awaitItem()
            assertEquals(toggle, item.dataOrNull())
            expectNoEvents()
        }
    }
}
