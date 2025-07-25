package de.stefan.lang.shapebyte.featureTogglesData

import de.stefan.lang.featureToggles.api.FeatureToggle
import de.stefan.lang.featureToggles.api.FeatureToggleState
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class FeatureToggleTest : BaseFeatureToggleDataTest() {
    @Test
    fun `isEnabled should return correct value`() {
        assertTrue(FeatureToggle("identifier", FeatureToggleState.ENABLED).isEnabled)
        assertFalse(FeatureToggle("identifier", FeatureToggleState.DISABLED).isEnabled)
    }

    @Test
    fun `isDisabled should return correct value`() {
        assertTrue(FeatureToggle("identifier", FeatureToggleState.DISABLED).isDisabled)
        assertFalse(FeatureToggle("identifier", FeatureToggleState.ENABLED).isDisabled)
    }

    @Test
    fun `isValid should return correct value`() {
        assertTrue(FeatureToggle("identifier", FeatureToggleState.ENABLED).isValid)
        assertTrue(FeatureToggle("iden tifier", FeatureToggleState.ENABLED).isValid)
        assertTrue(FeatureToggle("identifier 83839", FeatureToggleState.ENABLED).isValid)
        assertTrue(FeatureToggle("identifier 83839", FeatureToggleState.DISABLED).isValid)

        assertFalse(FeatureToggle("", FeatureToggleState.ENABLED).isValid)
        assertFalse(FeatureToggle(" ", FeatureToggleState.ENABLED).isValid)
        assertFalse(FeatureToggle("   ", FeatureToggleState.ENABLED).isValid)
        assertFalse(FeatureToggle("\n", FeatureToggleState.ENABLED).isValid)
        assertFalse(FeatureToggle("  \n", FeatureToggleState.ENABLED).isValid)
    }
}
