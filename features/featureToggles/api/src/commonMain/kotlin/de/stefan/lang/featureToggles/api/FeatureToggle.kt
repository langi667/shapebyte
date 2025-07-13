package de.stefan.lang.featureToggles.api

data class FeatureToggle(
    val identifier: String,
    val state: FeatureToggleState,
) {
    val isEnabled: Boolean = state == FeatureToggleState.ENABLED
    val isDisabled: Boolean = !isEnabled
    val isValid: Boolean = identifier.isNotBlank()
}
