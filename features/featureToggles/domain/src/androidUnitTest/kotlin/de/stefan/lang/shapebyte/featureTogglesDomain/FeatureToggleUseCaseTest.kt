package de.stefan.lang.shapebyte.featureTogglesDomain

import app.cash.turbine.test
import de.stefan.lang.featureToggles.api.FeatureToggle
import de.stefan.lang.featureToggles.api.FeatureToggleState
import de.stefan.lang.featureToggles.api.FeatureToggleUseCase
import de.stefan.lang.foundationCore.api.loadstate.LoadState
import de.stefan.lang.shapebyte.featureTogglesData.FeatureToggleDatasource
import de.stefan.lang.shapebyte.featureTogglesData.FeatureToggleRepository
import de.stefan.lang.shapebyte.featureTogglesData.impl.FeatureToggleError
import de.stefan.lang.shapebyte.featureTogglesDomain.impl.FeatureToggleUseCaseImpl
import de.stefan.lang.shapebyte.featureTogglesDomain.impl.LoadFeatureToggleUseCaseImpl
import io.mockk.coEvery
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertIs
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue
import io.mockk.mockk
import org.koin.core.component.get

class FeatureToggleUseCaseTest : BaseFeatureToggleDomainTest() {
    private val feature = "QUICK_WORKOUTS"

    private val datasource: FeatureToggleDatasource = mockk(relaxed = true)

    private val sut: FeatureToggleUseCase by lazy {
        FeatureToggleUseCaseImpl(
            featureId = feature,
            loadFeatureToggleUseCase = LoadFeatureToggleUseCaseImpl(
                repository = FeatureToggleRepository(
                    logger = get(),
                    dataSource = datasource,
                ),
                logger = get(),
                coroutineScopeProviding = get(),
                coroutineContextProviding = get()
            ),
        )
    }



    @Test
    fun `should return null if toggle is not set`() = test {
       coEvery { datasource.fetchFeatureToggle(any()) } returns LoadState.Error(FeatureToggleError.NotFound(""))

        sut.invoke().test {
            val item = awaitItem() as LoadState.Error
            assertNull(item.dataOrNull())
            assertIs<FeatureToggleError.NotFound>(item.errorOrNull())
            expectNoEvents()
        }
    }


    @Test
    fun `should return false if toggle is not set`() = test {
        coEvery { datasource.fetchFeatureToggle(any()) } returns LoadState.Error(FeatureToggleError.NotFound(""))

        sut.isEnabled.test {
            assertFalse(awaitItem())
            expectNoEvents()
        }
    }


   @Test
   fun `should return toggle is set`() = test {
       coEvery { datasource.fetchFeatureToggle(any()) } returns LoadState.Error(FeatureToggleError.NotFound(""))
       coEvery { datasource.fetchFeatureToggle(feature) } returns LoadState.Success(FeatureToggle(feature, FeatureToggleState.ENABLED))

       sut.invoke().test {
           val item = awaitItem() as LoadState.Success
           assertNotNull(item.data)
           expectNoEvents()
       }
   }


   @Test
   fun `should return true if toggle is set and enabled`() = test {
       coEvery { datasource.fetchFeatureToggle(feature) } returns LoadState.Success(FeatureToggle(feature, FeatureToggleState.ENABLED))

       sut.isEnabled.test {
           assertTrue(awaitItem())
           expectNoEvents()
       }
   }


   @Test
   fun `should return false if toggle is set and disabled`() = test {
       coEvery { datasource.fetchFeatureToggle(any()) } returns LoadState.Error(FeatureToggleError.NotFound(""))
       coEvery { datasource.fetchFeatureToggle(feature) } returns LoadState.Success(FeatureToggle(feature, FeatureToggleState.DISABLED))

       sut.isEnabled.test {
           assertFalse(awaitItem())
           expectNoEvents()
       }
   }
}