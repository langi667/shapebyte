package de.stefan.lang.shapebyte.features.workout.item.timed.ui

import de.stefan.lang.designsystem.ColorDescriptor
import de.stefan.lang.foundationCore.assets.ImageAsset
import de.stefan.lang.foundationUI.buttons.ButtonState
import de.stefan.lang.shapebyte.features.workout.item.core.data.ImageContaining
import de.stefan.lang.shapebyte.features.workout.item.core.data.Item

data class TimedWorkoutViewData(
    val title: String = "",
    val remaining: String = "",
    val setDuration: Int = 0, // in seconds, duration of the current set, useful for progress spinner
    val elapsedTotal: String = "",
    val remainingTotal: String = "",
    val progressTotal: Float = 0.0f,
    val playButtonState: ButtonState = ButtonState.Hidden,
    val pauseButtonState: ButtonState = ButtonState.Hidden,
    val stopButtonState: ButtonState = ButtonState.Hidden,
    val exercise: Item? = null,
    val backgroundColor: ColorDescriptor.Themed = ColorDescriptor.Background,
    val launchState: TimedWorkoutViewModel.LaunchState = TimedWorkoutViewModel.LaunchState.Idle,
) {
    // TODO: test
    val playButtonVisible: Boolean
        get() = playButtonState.isVisible

    val pauseButtonVisible: Boolean
        get() = pauseButtonState.isVisible

    val stopButtonVisible: Boolean
        get() = stopButtonState.isVisible

    // TODO: Test
    val exerciseImage: ImageAsset? get() = (exercise as? ImageContaining)?.imageAsset
}
