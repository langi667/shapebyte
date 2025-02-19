package de.stefan.lang.shapebyte.featureToggles.data.impl
import kotlinx.serialization.Serializable

@Serializable
data class FeatureToggleData(
    val identifier: String = "",
    val state: String = "",
)
