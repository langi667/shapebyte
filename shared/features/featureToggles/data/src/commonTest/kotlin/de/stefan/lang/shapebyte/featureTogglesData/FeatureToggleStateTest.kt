package de.stefan.lang.shapebyte.featureTogglesData

import de.stefan.lang.featureToggles.api.FeatureToggleState
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class FeatureToggleStateTest : BaseFeatureToggleDataTest() {
    @Test
    fun `isEnabled should return true or false depending on FeatureToggleState`() {
        assertTrue(FeatureToggleState.ENABLED.isEnabled)
        assertFalse(FeatureToggleState.DISABLED.isEnabled)
    }
}
