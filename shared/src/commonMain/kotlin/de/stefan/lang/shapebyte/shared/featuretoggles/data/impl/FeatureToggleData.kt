package de.stefan.lang.shapebyte.shared.featuretoggles.data.impl
import kotlinx.serialization.Serializable

@Serializable
data class FeatureToggleData(
    val identifier: String = "",
    val state: String = "",
)
