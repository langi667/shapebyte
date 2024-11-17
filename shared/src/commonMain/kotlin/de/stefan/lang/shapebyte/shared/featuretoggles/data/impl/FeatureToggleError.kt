package de.stefan.lang.shapebyte.shared.featuretoggles.data.impl

sealed class FeatureToggleError : Throwable() {
    data class NotFound(val identifier: String) : FeatureToggleError()
}
