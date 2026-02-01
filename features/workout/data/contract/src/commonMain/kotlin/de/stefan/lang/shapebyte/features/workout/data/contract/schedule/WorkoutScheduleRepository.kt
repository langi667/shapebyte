package de.stefan.lang.shapebyte.features.workout.data.contract.schedule

// TODO: Test
class WorkoutScheduleRepository(
    private val datasource: WorkoutScheduleDatasource,
) {
    suspend fun currentWorkoutScheduleEntry() = datasource.currentWorkoutScheduleEntry()
}
