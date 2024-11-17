package de.stefan.lang.shapebyte.shared.featuretoggles.data

enum class FeatureToggleState {
    ENABLED,
    DISABLED,
    ;

    // TODO: Test
    val isEnabled: Boolean
        get() = this == ENABLED
}
