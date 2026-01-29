package de.stefan.lang.shapebyte.featureToggles.data.contract

public enum class FeatureToggleState {
    ENABLED,
    DISABLED,
    ;

    public val isEnabled: Boolean
        get() = this == ENABLED
}
