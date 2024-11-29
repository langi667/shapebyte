package de.stefan.lang.shapebyte.shared.featuretoggles.data

enum class FeatureToggleState {
    ENABLED,
    DISABLED,
    ;

    val isEnabled: Boolean
        get() = this == ENABLED
}
