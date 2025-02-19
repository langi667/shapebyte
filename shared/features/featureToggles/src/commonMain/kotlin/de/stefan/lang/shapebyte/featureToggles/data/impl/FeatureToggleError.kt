package de.stefan.lang.shapebyte.featureToggles.data.impl

sealed class FeatureToggleError : Throwable() {
    data class NotFound(val identifier: String) : FeatureToggleError()
}
