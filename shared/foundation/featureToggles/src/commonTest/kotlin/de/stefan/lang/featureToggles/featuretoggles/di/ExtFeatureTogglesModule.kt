package de.stefan.lang.featureToggles.featuretoggles.di

import de.stefan.lang.featureToggles.FeatureTogglesModule
import de.stefan.lang.featureToggles.data.FeatureToggleDatasource
import de.stefan.lang.featureToggles.data.FeatureToggleRepository
import de.stefan.lang.featureToggles.data.impl.FeatureToggleDatasourceMock
import org.koin.core.component.get

val FeatureTogglesModule.featureToggleRepository: FeatureToggleRepository
    get() = this.get()

val FeatureTogglesModule.featureToggleDatasource: FeatureToggleDatasource
    get() = this.get()

val FeatureTogglesModule.featureToggleDatasourceMock: FeatureToggleDatasourceMock
    get() = featureToggleDatasource as FeatureToggleDatasourceMock

//
// val DPI.quickWorkoutsDatasourceMock: QuickWorkoutsDatasourceMocks
//    get() = this.get<QuickWorkoutsDatasource>() as QuickWorkoutsDatasourceMocks
//
// val DPI.workoutHistoryDataSourceMocks: WorkoutHistoryDataSourceMocks
//    get() = this.get<WorkoutHistoryDataSource>() as WorkoutHistoryDataSourceMocks
