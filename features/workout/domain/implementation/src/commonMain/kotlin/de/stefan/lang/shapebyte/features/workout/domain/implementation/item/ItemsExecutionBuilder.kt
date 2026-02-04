package de.stefan.lang.shapebyte.features.workout.domain.implementation.item

import de.stefan.lang.shapebyte.features.workout.data.contract.WorkoutType
import de.stefan.lang.shapebyte.features.workout.data.contract.exercise.Exercise
import de.stefan.lang.shapebyte.features.workout.data.contract.exercise.ExerciseExecutionInfo
import de.stefan.lang.shapebyte.features.workout.data.contract.exercise.IntervalExerciseInfo
import de.stefan.lang.shapebyte.features.workout.data.contract.item.ItemSet
import de.stefan.lang.shapebyte.features.workout.domain.contract.item.ItemsExecution
import de.stefan.lang.shapebyte.features.workout.domain.contract.item.ItemsExecutionBuilding
import de.stefan.lang.shapebyte.features.workout.domain.implementation.timed.TimedItemExecutionImpl
import de.stefan.lang.utils.logging.contract.Logger

public class ItemsExecutionBuilder(
    private val logger: Logger,
) : ItemsExecutionBuilding {
    public companion object {
        private val DefaultHighIntenseExercise = Exercise("High", "high-intense-exercise.png")
        private val DefaultLowIntenseExercise = Exercise("Low", "low-intense-exercise.png")
    }

    public override fun buildWith(workoutType: WorkoutType): ItemsExecution {
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

            TimedItemExecutionImpl(
                item,
                sets = sets,
                logger = logger,
            )
        }

        return ItemsExecutionImpl(executions, logger)
    }
}
