package de.stefan.lang.shapebyte.features.workout.workoutDomain

import de.stefan.lang.shapebyte.features.workout.workoutData.Exercise
import de.stefan.lang.shapebyte.features.workout.workoutData.ExerciseExecutionInfo
import de.stefan.lang.shapebyte.features.workout.workoutData.IntervalExerciseInfo
import de.stefan.lang.shapebyte.features.workout.workoutData.ItemSet
import de.stefan.lang.shapebyte.features.workout.workoutData.WorkoutType


class ItemsExecutionBuilder {
    companion object {
        private val DefaultHighIntenseExercise = Exercise("High", "high-intense-exercise.png")
        private val DefaultLowIntenseExercise = Exercise("Low", "low-intense-exercise.png")
    }

    fun buildWith(workoutType: WorkoutType): ItemsExecution {
        when (workoutType) {
            is WorkoutType.Timed.Interval -> {
                return buildWithTimedInterval(workoutType)
            }
        }
    }

    private fun buildWithTimedInterval(workoutType: WorkoutType.Timed.Interval): ItemsExecution {
        val executions = List(workoutType.rounds * 2) {
            val currRound = it + 1

            val item: ExerciseExecutionInfo
            val sets: List<ItemSet.Timed.Seconds>

            if (currRound.mod(2) == 0) { // low interval
                item = ExerciseExecutionInfo(
                    exercise = DefaultLowIntenseExercise,
                    intervalExerciseInfo = IntervalExerciseInfo.LOW,
                )

                sets = List(workoutType.lowDurationSec) {
                    ItemSet.Timed.Seconds(1)
                }
            } else {
                item = ExerciseExecutionInfo(
                    exercise = DefaultHighIntenseExercise,
                    intervalExerciseInfo = IntervalExerciseInfo.HIGH,
                )

                sets = List(workoutType.highDurationSec) {
                    ItemSet.Timed.Seconds(1)
                }
            }

            WorkoutDomainModule.createTimedItemExecution(item, sets)
        }

        return WorkoutDomainModule.createItemsExecution(executions)
    }
}
