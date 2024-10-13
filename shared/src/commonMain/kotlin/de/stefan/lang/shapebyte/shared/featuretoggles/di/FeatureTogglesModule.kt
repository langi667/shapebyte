package de.stefan.lang.shapebyte.shared.featuretoggles.di

import de.stefan.lang.shapebyte.shared.featuretoggles.data.FeatureToggleDatasource
import de.stefan.lang.shapebyte.shared.featuretoggles.data.FeatureToggleRepository
import de.stefan.lang.shapebyte.shared.featuretoggles.data.impl.FeatureToggleDatasourceImpl
import de.stefan.lang.shapebyte.shared.featuretoggles.data.impl.FeatureToggleDatasourceMock
import de.stefan.lang.shapebyte.shared.featuretoggles.domain.LoadFeatureToggleUseCase
import de.stefan.lang.shapebyte.utils.dicore.DIModule
import org.koin.core.module.Module
import org.koin.dsl.module

object FeatureTogglesModule : DIModule {
    override val module: Module = module {
        single<LoadFeatureToggleUseCase> { LoadFeatureToggleUseCase(logger = get(), repository = get()) }
        single<FeatureToggleRepository> { FeatureToggleRepository(logger = get(), datasource = get()) }
        single<FeatureToggleDatasource> { FeatureToggleDatasourceImpl(logger = get()) }
    }

    override val testModule: Module = module {
        single<LoadFeatureToggleUseCase> { LoadFeatureToggleUseCase(logger = get(), repository = get()) }
        single<FeatureToggleRepository> { FeatureToggleRepository(logger = get(), datasource = get()) }
        single<FeatureToggleDatasource> { FeatureToggleDatasourceMock(logger = get()) }
    }
}
