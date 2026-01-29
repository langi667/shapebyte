package de.stefan.lang.shapebyte.featureToggles.data.contract

import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class FeatureToggleStateTest {
    @Test
    fun `isEnabled should return true or false depending on FeatureToggleState`() {
        assertTrue(FeatureToggleState.ENABLED.isEnabled)
        assertFalse(FeatureToggleState.DISABLED.isEnabled)
    }
}
