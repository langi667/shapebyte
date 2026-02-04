package de.stefan.lang.shapebyte.featureToggles.data.implementation

import de.stefan.lang.shapebyte.featureToggles.data.contract.FeatureToggle
import de.stefan.lang.shapebyte.featureToggles.data.contract.FeatureToggleState

public class FeatureToggleMapper {
    public fun map(dataList: List<FeatureToggleData>): List<FeatureToggle> = dataList
        .map { map(it) }

    public fun map(data: FeatureToggleData): FeatureToggle {
        val retVal = FeatureToggle(
            identifier = data.identifier,
            state = stateFromString(data.state),
        )

        return retVal
    }

    private fun stateFromString(state: String): FeatureToggleState = when (state) {
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
