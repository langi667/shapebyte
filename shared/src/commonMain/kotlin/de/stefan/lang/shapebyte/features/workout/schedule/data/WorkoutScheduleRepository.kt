package de.stefan.lang.shapebyte.features.workout.schedule.data

// TODO: Test
class WorkoutScheduleRepository(
    private val datasource: WorkoutScheduleDatasource,
) {
    suspend fun currentWorkoutScheduleEntry() = datasource.currentWorkoutScheduleEntry()
}
