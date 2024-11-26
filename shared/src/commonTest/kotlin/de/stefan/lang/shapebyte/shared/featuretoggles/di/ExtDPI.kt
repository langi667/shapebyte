package de.stefan.lang.shapebyte.shared.featuretoggles.di

import de.stefan.lang.shapebyte.di.DPI
import de.stefan.lang.shapebyte.features.workout.history.data.WorkoutHistoryDataSource
import de.stefan.lang.shapebyte.features.workout.history.data.mocks.WorkoutHistoryDataSourceMocks
import de.stefan.lang.shapebyte.features.workout.quick.data.QuickWorkoutsDatasource
import de.stefan.lang.shapebyte.features.workout.quick.data.mocks.QuickWorkoutsDatasourceMocks
import de.stefan.lang.shapebyte.shared.featuretoggles.data.FeatureToggleDatasource
import de.stefan.lang.shapebyte.shared.featuretoggles.data.impl.FeatureToggleDatasourceMock
import org.koin.core.component.get

val DPI.featureToggleDatasource: FeatureToggleDatasource
    get() = this.get()

val DPI.featureToggleDatasourceMock: FeatureToggleDatasourceMock
    get() = featureToggleDatasource as FeatureToggleDatasourceMock

val DPI.quickWorkoutsDatasourceMock: QuickWorkoutsDatasourceMocks
    get() = this.get<QuickWorkoutsDatasource>() as QuickWorkoutsDatasourceMocks

val DPI.workoutHistoryDataSourceMocks: WorkoutHistoryDataSourceMocks
    get() = this.get<WorkoutHistoryDataSource>() as WorkoutHistoryDataSourceMocks
