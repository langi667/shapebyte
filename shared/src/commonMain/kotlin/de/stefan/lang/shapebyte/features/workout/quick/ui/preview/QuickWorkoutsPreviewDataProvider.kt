package de.stefan.lang.shapebyte.features.workout.quick.ui.preview

import de.stefan.lang.shapebyte.features.workout.quick.data.mocks.QuickWorkoutsDatasourceMocks

// TODO: consider using mock data from file
object QuickWorkoutsPreviewDataProvider {
    private val datasource = QuickWorkoutsDatasourceMocks()

    val previewData = datasource.workouts
}
