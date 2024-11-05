package de.stefan.lang.shapebyte.shared.featuretoggles.di

import de.stefan.lang.shapebyte.shared.featuretoggles.data.FeatureToggleDatasource
import de.stefan.lang.shapebyte.shared.featuretoggles.data.FeatureToggleRepository
import de.stefan.lang.shapebyte.shared.featuretoggles.data.impl.FeatureToggleDatasourceImpl
import de.stefan.lang.shapebyte.shared.featuretoggles.data.impl.FeatureToggleDatasourceMock
import de.stefan.lang.shapebyte.shared.featuretoggles.domain.LoadFeatureToggleUseCase
import de.stefan.lang.shapebyte.utils.dicore.DIModuleDeclaration
import org.koin.core.component.get

interface FeatureTogglesModuleProviding {
    fun loadFeatureToggleUseCase(): LoadFeatureToggleUseCase
}

object FeatureTogglesModule :
    DIModuleDeclaration(
        allEnvironments = {
            single<LoadFeatureToggleUseCase> { LoadFeatureToggleUseCase(logger = get(), repository = get()) }
            single<FeatureToggleRepository> { FeatureToggleRepository(logger = get(), datasource = get()) }
        },
        appEnvironmentOnly = {
            single<FeatureToggleDatasource> { FeatureToggleDatasourceImpl(logger = get()) }
        },
        testEnvironmentOnly = {
            single<FeatureToggleDatasource> { FeatureToggleDatasourceMock(logger = get()) }
        },
    ),
    FeatureTogglesModuleProviding {
    override fun loadFeatureToggleUseCase(): LoadFeatureToggleUseCase {
        return get()
    }
}
