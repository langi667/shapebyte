package de.stefan.lang.shapebyte.featureToggles.data.contract

public data class FeatureToggle(
    val identifier: String,
    val state: FeatureToggleState,
) {
    public val isEnabled: Boolean = state == FeatureToggleState.ENABLED
    public val isDisabled: Boolean = !isEnabled
    public val isValid: Boolean = identifier.isNotBlank()
}
