package de.stefan.lang.featureToggles.data

import de.stefan.lang.coretest.CoreTest
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class FeatureToggleStateTest : CoreTest() {
    @Test
    fun `isEnabled should return true or false depending on FeatureToggleState`() {
        assertTrue(FeatureToggleState.ENABLED.isEnabled)
        assertFalse(FeatureToggleState.DISABLED.isEnabled)
    }
}
