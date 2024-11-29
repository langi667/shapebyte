package de.stefan.lang.shapebyte.shared.featuretoggles.data

import de.stefan.lang.shapebyte.utils.BaseTest
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class FeatureToggleStateTest : BaseTest() {
    @Test
    fun `isEnabled should return true or false depending on FeatureToggleState`() {
        assertTrue(FeatureToggleState.ENABLED.isEnabled)
        assertFalse(FeatureToggleState.DISABLED.isEnabled)
    }
}
