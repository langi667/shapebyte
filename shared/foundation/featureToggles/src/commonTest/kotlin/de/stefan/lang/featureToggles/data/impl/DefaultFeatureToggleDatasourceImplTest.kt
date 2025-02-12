package de.stefan.lang.featureToggles.data.impl

import de.stefan.lang.coreCoroutinesProvidingTest.TestCoroutineContextProvider
import de.stefan.lang.coretest.CoreTest
import de.stefan.lang.coreutils.logging.mocks.SilentLogger
import de.stefan.lang.featureToggles.data.FeatureToggleState
import de.stefan.lang.foundationCore.assets.mocks.FileAssetLoaderMock
import de.stefan.lang.foundationCore.loadstate.LoadState
import kotlin.test.Test
import kotlin.test.assertEquals

class DefaultFeatureToggleDatasourceImplTest : CoreTest() {
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

        val featureToggleID1 = sut.fetchFeatureToggle("ID_1") as LoadState.Success
        assertEquals("ID_1", featureToggleID1.data.identifier)
        assertEquals(FeatureToggleState.ENABLED, featureToggleID1.data.state)

        val featureToggleID2 = sut.fetchFeatureToggle("ID_2") as LoadState.Success
        assertEquals("ID_2", featureToggleID2.data.identifier)
        assertEquals(FeatureToggleState.DISABLED, featureToggleID2.data.state)

        val featureToggleID3 = sut.fetchFeatureToggle("ID_3") as LoadState.Success
        assertEquals("ID_3", featureToggleID3.data.identifier)
        assertEquals(FeatureToggleState.DISABLED, featureToggleID3.data.state)

        val featureToggleID4 = sut.fetchFeatureToggle("ID_4") as LoadState.Success
        assertEquals("ID_4", featureToggleID4.data.identifier)
        assertEquals(FeatureToggleState.DISABLED, featureToggleID4.data.state)
    }

    private fun createSUT(defaultMockContent: String): DefaultFeatureToggleDatasourceImpl {
        val loader = FileAssetLoaderMock()
        loader.mockFileContent(
            DefaultFeatureToggleDatasourceImpl.defaultAssetFile,
            defaultMockContent,
        )
        return DefaultFeatureToggleDatasourceImpl(
            logger = SilentLogger(),
            assetLoader = loader,
            coroutineContextProviding = TestCoroutineContextProvider,
        )
    }
}
