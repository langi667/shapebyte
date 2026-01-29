package de.stefan.lang.shapebyte.featureTogglesData

enum class FeatureToggleState {
    ENABLED,
    DISABLED,
    ;

    val isEnabled: Boolean
        get() = this == ENABLED
}
