package de.stefan.lang.shapebyte.featureTogglesData.impl

sealed class FeatureToggleError : Throwable() {
    data class NotFound(val identifier: String) : FeatureToggleError()
}
