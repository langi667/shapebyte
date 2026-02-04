package de.stefan.lang.shapebyte.featureToggles.data.implementation
import kotlinx.serialization.Serializable

@Serializable
public data class FeatureToggleData(
    public val identifier: String = "",
    public val state: String = "",
)
