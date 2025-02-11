package de.stefan.lang.featureToggles.data.impl

sealed class FeatureToggleError : Throwable() {
    data class NotFound(val identifier: String) : FeatureToggleError()
}
