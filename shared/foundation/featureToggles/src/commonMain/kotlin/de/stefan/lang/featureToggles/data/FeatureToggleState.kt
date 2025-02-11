package de.stefan.lang.featureToggles.data

enum class FeatureToggleState {
    ENABLED,
    DISABLED,
    ;

    val isEnabled: Boolean
        get() = this == ENABLED
}
