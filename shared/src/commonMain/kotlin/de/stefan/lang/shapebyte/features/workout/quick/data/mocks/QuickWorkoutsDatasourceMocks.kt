package de.stefan.lang.shapebyte.features.workout.quick.data.mocks

import de.stefan.lang.shapebyte.features.workout.core.data.Workout
import de.stefan.lang.shapebyte.features.workout.quick.data.QuickWorkoutsDatasource
import de.stefan.lang.shapebyte.utils.assets.ImageAsset
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class QuickWorkoutsDatasourceMocks : QuickWorkoutsDatasource {
    override fun fetchQuickWorkouts(): Flow<List<Workout>> = flow {
        emit(
            listOf(
                Workout(
                    name = "HIIT Core",
                    shortDescription = "20 min, Core, Legs",
                    image = ImageAsset(assetName = "sprints.png"),
                ),
                Workout(
                    name = "Sets & Reps",
                    shortDescription = "3 Sets 6 exercises",
                    image = ImageAsset(assetName = "sprints.png"),
                ),
                Workout(
                    name = "Warmup",
                    shortDescription = "40, min, Interval 15 sec.",
                    image = ImageAsset(assetName = "sprints.png"),
                ),
                Workout(
                    name = "Cooldown",
                    shortDescription = "20, min, Interval 1 Minute",
                    image = ImageAsset(assetName = "sprints.png"),
                ),
            ),
        )
    }
}
