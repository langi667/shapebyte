package de.stefan.lang.shapebyte.features.workout.workoutData.mocks

// TODO: Test
class WorkoutScheduleRepository(
    private val datasource: WorkoutScheduleDatasource,
) {
    suspend fun currentWorkoutScheduleEntry() = datasource.currentWorkoutScheduleEntry()
}
