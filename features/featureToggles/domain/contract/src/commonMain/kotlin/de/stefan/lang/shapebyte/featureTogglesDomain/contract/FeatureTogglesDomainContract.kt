package de.stefan.lang.shapebyte.featureTogglesDomain.contract

public interface FeatureTogglesDomainContract {
    public fun featureTogglesLoader(): LoadFeatureToggleUseCase
    public fun featureToggleUseCase(featureId: String): FeatureToggleUseCase
}
