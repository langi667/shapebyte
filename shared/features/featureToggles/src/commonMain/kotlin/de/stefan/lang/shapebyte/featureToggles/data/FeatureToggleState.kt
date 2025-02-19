package de.stefan.lang.shapebyte.featureToggles.data

enum class FeatureToggleState {
    ENABLED,
    DISABLED,
    ;

    val isEnabled: Boolean
        get() = this == ENABLED
}
