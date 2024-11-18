package de.stefan.lang.shapebyte.shared.featuretoggles.data.impl

import app.cash.turbine.test
import de.stefan.lang.shapebyte.shared.featuretoggles.data.FeatureToggleState
import de.stefan.lang.shapebyte.shared.loading.data.LoadState
import de.stefan.lang.shapebyte.utils.BaseCoroutineTest
import de.stefan.lang.shapebyte.utils.assets.mocks.FileAssetLoaderMock
import de.stefan.lang.shapebyte.utils.mocks.SilentLogger
import kotlin.test.Test
import kotlin.test.assertEquals

class FeatureToggleDatasourceImplTest : BaseCoroutineTest() {
    private val defaultToggles = """
        [
            {
                "identifier": "ID_1",
                "state": "ENABLED"
            },
            {
                "identifier": "ID_2",
                "state": "DISABLED"
            },
            {
                "identifier": "ID_3",
                "state": "UNKNOWN"
            },
            {
                "identifier": "ID_4",
                "state": "some unknown state"
            }
        ]
    """.trimIndent()

    @Test
    fun `fetch should return correct value`() = test {
        val sut = createSUT(defaultToggles)

        sut.fetchFeatureToggle("ID_1").test {
            val item = awaitItem()
            val dataItem = item as LoadState.Success

            assertEquals("ID_1", dataItem.data.identifier)
            assertEquals(FeatureToggleState.ENABLED, dataItem.data.state)

            expectNoEvents()
        }

        sut.fetchFeatureToggle("ID_2").test {
            val item = awaitItem()
            val dataItem = item as LoadState.Success

            assertEquals("ID_2", dataItem.data.identifier)
            assertEquals(FeatureToggleState.DISABLED, dataItem.data.state)

            expectNoEvents()
        }

        sut.fetchFeatureToggle("ID_3").test {
            val item = awaitItem()
            val dataItem = item as LoadState.Success

            assertEquals("ID_3", dataItem.data.identifier)
            assertEquals(FeatureToggleState.DISABLED, dataItem.data.state)

            expectNoEvents()
        }

        sut.fetchFeatureToggle("ID_4").test {
            val item = awaitItem()
            val dataItem = item as LoadState.Success

            assertEquals("ID_4", dataItem.data.identifier)
            assertEquals(FeatureToggleState.DISABLED, dataItem.data.state)

            expectNoEvents()
        }
    }

    private fun createSUT(defaultMockContent: String): FeatureToggleDatasourceImpl {
        val loader = FileAssetLoaderMock()
        loader.mockFileContent(
            FeatureToggleDatasourceImpl.defaultAssetFile,
            defaultMockContent,
        )
        return FeatureToggleDatasourceImpl(
            SilentLogger(),
            loader,
        )
    }
}
