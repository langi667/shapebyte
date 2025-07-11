package de.stefan.lang.shapebyte.featureTogglesData.impl

import de.stefan.lang.featureToggles.api.FeatureToggle
import de.stefan.lang.featureToggles.api.FeatureToggleState

class FeatureToggleMapper {
    fun map(dataList: List<FeatureToggleData>): List<FeatureToggle> = dataList
        .map { map(it) }

    fun map(data: FeatureToggleData): FeatureToggle {
        val retVal = FeatureToggle(
            identifier = data.identifier,
            state = stateFromString(data.state),
        )

        return retVal
    }

    private fun stateFromString(state: String) = when (state) {
        FeatureToggleState.ENABLED.name -> {
            FeatureToggleState.ENABLED
        }
        FeatureToggleState.DISABLED.name -> {
            FeatureToggleState.DISABLED
        }
        else -> {
            FeatureToggleState.DISABLED
        }
    }
}
