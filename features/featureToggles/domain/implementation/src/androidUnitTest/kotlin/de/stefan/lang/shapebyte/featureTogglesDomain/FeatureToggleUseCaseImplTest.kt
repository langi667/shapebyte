package de.stefan.lang.shapebyte.featureTogglesDomain

import app.cash.turbine.test
import de.stefan.lang.coroutines.CoroutinesModule
import de.stefan.lang.shapebyte.featureToggles.data.contract.FeatureToggle
import de.stefan.lang.shapebyte.featureToggles.data.contract.FeatureToggleState
import de.stefan.lang.foundation.core.contract.loadstate.LoadState
import de.stefan.lang.shapebyte.featureToggles.data.contract.FeatureToggleDatasource
import de.stefan.lang.shapebyte.featureToggles.data.contract.FeatureToggleRepository
import de.stefan.lang.shapebyte.featureToggles.data.contract.FeatureToggleError
import de.stefan.lang.shapebyte.featureTogglesDomain.contract.FeatureToggleUseCase
import de.stefan.lang.shapebyte.featureToggles.domain.implementation.FeatureToggleUseCaseImpl
import de.stefan.lang.shapebyte.featureToggles.domain.implementation.LoadFeatureToggleUseCaseImpl
import de.stefan.lang.utils.logging.LoggingModule
import io.mockk.coEvery
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertIs
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue
import io.mockk.mockk
import org.koin.core.component.get

class FeatureToggleUseCaseImplTest : BaseFeatureToggleDomainTest() {
    private val feature = "QUICK_WORKOUTS"

    private val datasource: FeatureToggleDatasource = mockk(relaxed = true)

    private val sut: FeatureToggleUseCaseImpl by lazy {
        FeatureToggleUseCaseImpl(
            featureId = feature,
            loadFeatureToggleUseCase = LoadFeatureToggleUseCaseImpl(
                repository = FeatureToggleRepository(
                    logger = LoggingModule.logger(),
                    dataSource = datasource,
                ),
                logger = LoggingModule.logger(),
                coroutineScopeProvider = CoroutinesModule.coroutineScopeProvider(),
                coroutineContextProvider = CoroutinesModule.coroutineContextProvider()
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
