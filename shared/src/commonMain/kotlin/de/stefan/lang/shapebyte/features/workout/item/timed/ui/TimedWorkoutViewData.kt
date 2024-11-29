package de.stefan.lang.shapebyte.features.workout.item.timed.ui

import de.stefan.lang.shapebyte.features.workout.item.core.data.ImageContaining
import de.stefan.lang.shapebyte.features.workout.item.core.data.Item
import de.stefan.lang.shapebyte.utils.assets.ImageAsset
import de.stefan.lang.shapebyte.utils.buttons.ButtonState
import de.stefan.lang.shapebyte.utils.designsystem.data.ColorDescriptor

data class TimedWorkoutViewData(
    val title: String,
    val remaining: String,
    val setDuration: Int, // in seconds, duration of the current set, useful for progress spinner
    val elapsedTotal: String,
    val remainingTotal: String,
    val progressTotal: Float,
    val playButtonState: ButtonState,
    val pauseButtonState: ButtonState,
    val stopButtonState: ButtonState,
    val item: Item? = null,
    val backgroundColor: ColorDescriptor.Themed,
    val launchState: TimedWorkoutViewModel.LaunchState,
) {
    // TODO: test
    val playButtonVisible: Boolean
        get() = playButtonState.isVisible

    val pauseButtonVisible: Boolean
        get() = pauseButtonState.isVisible

    val stopButtonVisible: Boolean
        get() = stopButtonState.isVisible

    // TODO: Test
    val exerciseImage: ImageAsset? get() = (item as? ImageContaining)?.imageAsset
}
