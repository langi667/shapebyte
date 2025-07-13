package de.stefan.lang.featureToggles.api

enum class FeatureToggleState {
    ENABLED,
    DISABLED,
    ;

    val isEnabled: Boolean
        get() = this == ENABLED
}
