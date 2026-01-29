package de.stefan.lang.shapebyte.featureToggles.data.implementation
import kotlinx.serialization.Serializable

@Serializable
data class FeatureToggleData(
    val identifier: String = "",
    val state: String = "",
)
