package de.stefan.lang.shapebyte.featuretoggles.di

import de.stefan.lang.shapebyte.featuretoggles.data.FeatureToggleDatasource
import de.stefan.lang.shapebyte.featuretoggles.data.FeatureToggleRepository
import de.stefan.lang.shapebyte.featuretoggles.data.impl.FeatureToggleDatasourceImpl
import de.stefan.lang.shapebyte.utils.dicore.DIModule
import org.koin.core.module.Module
import org.koin.dsl.module

object FeatureTogglesModule : DIModule {
    override val module: Module = module {
        single<FeatureToggleRepository> { FeatureToggleRepository(logger = get(), datasource = get()) }
        single<FeatureToggleDatasource> { FeatureToggleDatasourceImpl(logger = get()) }
    }

    override val testModule: Module = module {
    }
}
