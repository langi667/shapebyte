package de.stefan.lang.shapebyte.featureToggles.data.contract

public sealed class FeatureToggleError : Throwable() {
    public data class NotFound(val identifier: String) : FeatureToggleError()
}
