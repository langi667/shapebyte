package de.stefan.lang.shapebyte.features.workout.quick.data

import de.stefan.lang.shapebyte.features.workout.core.data.Workout
import kotlinx.coroutines.flow.Flow

interface QuickWorkoutsDatasource {
    fun fetchQuickWorkouts(): Flow<List<Workout>>
}
