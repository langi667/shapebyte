package de.stefan.lang.shapebyte.android.features.workout.timed.ui

import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import de.stefan.lang.foundationCore.assets.ImageAsset
import de.stefan.lang.foundationUI.buttons.ButtonState
import de.stefan.lang.foundationUI.viewmodel.UIState
import de.stefan.lang.shapebyte.android.designsystem.ui.With
import de.stefan.lang.shapebyte.android.designsystem.ui.color
import de.stefan.lang.shapebyte.android.designsystem.ui.components.text.BodyMedium
import de.stefan.lang.shapebyte.android.designsystem.ui.components.text.DisplayLarge
import de.stefan.lang.shapebyte.android.designsystem.ui.components.text.LabelSmall
import de.stefan.lang.shapebyte.android.designsystem.ui.components.text.TitleMedium
import de.stefan.lang.shapebyte.android.designsystem.ui.withData
import de.stefan.lang.shapebyte.android.navigation.NavigationHandler
import de.stefan.lang.shapebyte.android.shared.background.ui.RadialBackgroundView
import de.stefan.lang.shapebyte.android.shared.buttons.ui.PauseButton
import de.stefan.lang.shapebyte.android.shared.buttons.ui.PlayButton
import de.stefan.lang.shapebyte.android.shared.buttons.ui.RoundedImageButtonAppearance
import de.stefan.lang.shapebyte.android.shared.buttons.ui.StopButton
import de.stefan.lang.shapebyte.android.shared.image.ui.AsyncImage
import de.stefan.lang.shapebyte.android.shared.preview.ui.PreviewContainer
import de.stefan.lang.shapebyte.android.shared.progress.ui.GradientProgressIndicatorLarge
import de.stefan.lang.shapebyte.features.workout.TimedWorkoutViewData
import de.stefan.lang.shapebyte.features.workout.TimedWorkoutViewModel
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.compose.getViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun TimedWorkoutView(
    workoutId: Int,
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    val viewModel: TimedWorkoutViewModel = getViewModel(
        parameters = {
            parametersOf(NavigationHandler(navController))
        }
    )

    LaunchedEffect("Initial") {
        viewModel.load(workoutId)
    }

    val uiState = viewModel.state.collectAsStateWithLifecycle().value
    TimedWorkoutView(
        state = uiState,
        modifier = modifier,
        onCloseClicked = { viewModel.onCloseClicked() }
    )
}

@Composable
fun TimedWorkoutView(
    state: UIState,
    modifier: Modifier = Modifier,
    onCloseClicked: () -> Unit = {},
) {
    when (state) {
        is UIState.Loading -> {
            /* TODO */
        }

        is UIState.Idle -> {
            /* TODO */
        }

        is UIState.Data<*> -> {
            TimedWorkoutView(
                state.data as? TimedWorkoutViewData,
                modifier,
                onCloseClicked = onCloseClicked
            )
        }
    }
}

@Composable
fun TimedWorkoutView(
    data: TimedWorkoutViewData?,
    modifier: Modifier = Modifier,
    onCloseClicked: () -> Unit = {},
) {
    val image: ImageAsset? = data?.exerciseImage
    val progressTotal = data?.progressTotal ?: 0.0f

    TimedWorkoutView(
        title = data?.title ?: "",
        remaining = data?.remaining ?: "",
        elapsedTotal = data?.elapsedTotal ?: "",
        remainingTotal = data?.remainingTotal ?: "",
        playButtonState = data?.playButtonState ?: ButtonState.Hidden,
        pauseButtonState = data?.pauseButtonState ?: ButtonState.Hidden,
        stopButtonState = data?.stopButtonState ?: ButtonState.Hidden,
        exerciseImage = image,
        progress = progressTotal,
        modifier = modifier,
        backgroundColor = data?.backgroundColor?.color(),
        onCloseClicked = onCloseClicked,
    )
}

@Composable
fun TimedWorkoutView(
    title: String,
    remaining: String,
    elapsedTotal: String,
    remainingTotal: String,
    pauseButtonState: ButtonState,
    stopButtonState: ButtonState,
    playButtonState: ButtonState,
    exerciseImage: ImageAsset?,
    progress: Float,
    modifier: Modifier = Modifier,
    backgroundColor: Color? = null,
    onCloseClicked: () -> Unit = {},
) = With { theme ->
    Box(modifier.fillMaxSize()) {
        RadialBackgroundView(
            radialBackground = backgroundColor,
            topView = {
                TimerView(
                    title = title,
                    remaining = remaining,
                    elapsedTotal = elapsedTotal,
                    remainingTotal = remainingTotal,
                    onCloseClicked = onCloseClicked,
                )
            },
        ) {
            Row(
                Modifier
                    .fillMaxWidth()
                    .offset(y = -theme.spacings.tiny.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Row(
                    horizontalArrangement = Arrangement.Absolute.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    val pauseButtonAlpha =
                        fadeAnimation(pauseButtonState.isVisible, "pauseButtonAlpha")
                    val spacingsBetween = theme.spacings.small.dp

                    PauseButton(
                        modifier = Modifier
                            .alpha(pauseButtonAlpha),
                    ) { pauseButtonState.onClickAction?.invoke() }

                    Spacer(Modifier.width(spacingsBetween))

                    ExerciseView(
                        playButtonState = playButtonState,
                        exerciseImage = exerciseImage,
                        progress = progress,
                    )

                    Spacer(Modifier.width(spacingsBetween))

                    val stopButtonAlpha =
                        fadeAnimation(stopButtonState.isVisible, "stopButtonAlpha")
                    StopButton(
                        modifier = Modifier
                            .alpha(stopButtonAlpha),
                    ) { stopButtonState.onClickAction?.invoke() }
                }
            }
        }
    }
}

@Composable
private fun TimerView(
    title: String,
    remaining: String,
    elapsedTotal: String,
    remainingTotal: String,
    modifier: Modifier = Modifier,
    onCloseClicked: () -> Unit = {},
) = With { theme ->
    Column(
        modifier
            .fillMaxWidth()
            .padding(theme.spacings.small.dp),
    ) {
        AppToolBar(title = title, onCloseClicked = onCloseClicked)
        Spacer(Modifier.height(theme.spacings.large.dp))
        DisplayLarge(
            text = remaining,
            modifier = Modifier.fillMaxWidth(),
            textAlignment = TextAlign.Center,
        )

        Spacer(Modifier.height(theme.spacings.small.dp))

        Row(Modifier.fillMaxWidth()) {
            Column {
                BodyMedium(elapsedTotal)
                LabelSmall("Elapsed")
            }
            Spacer(
                Modifier
                    .fillMaxWidth()
                    .weight(2.0f),
            )
            Column(horizontalAlignment = Alignment.End) {
                BodyMedium(remainingTotal)
                LabelSmall("Remaining")
            }
        }

        Spacer(Modifier.height(theme.spacings.small.dp))
    }
}

@Composable
private fun ExerciseView(
    playButtonState: ButtonState,
    exerciseImage: ImageAsset?,
    progress: Float,
) {
    Box(contentAlignment = Alignment.Center) {
        ImageAndProgress(
            progress = progress,
            image = exerciseImage,
        )

        PlayButton(
            contentDescription = "Start Workout",
            onClick = playButtonState.onClickAction,
            modifier = Modifier
                .alpha(fadeAnimation(playButtonState.isVisible, "PlayButtonFade")),
        )
    }
}

@Composable
private fun AppToolBar(
    title: String,
    modifier: Modifier = Modifier,
    onCloseClicked: () -> Unit = {},
) = With { theme ->
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        val buttonSize = theme.dimensions.small
        Spacer(Modifier.size(buttonSize.dp))
        Box(
            Modifier
                .fillMaxWidth()
                .weight(2.0f),
            contentAlignment = Alignment.Center,
        ) {
            TitleMedium(title, Modifier)
        }

        IconButton(onCloseClicked, modifier = Modifier.size(buttonSize.dp)) {
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = "Close Timed View",
                tint = Color.White,
            )
        }
    }
}

@Composable
private fun fadeAnimation(
    fadeIn: Boolean,
    label: String,
): Float = withData { theme ->
    val animationDuration = theme.animationDurations.short.toInt()
    val alphaAnimation by animateFloatAsState(
        targetValue = if (fadeIn) 1f else 0f,
        animationSpec = tween(durationMillis = animationDuration),
        label = label,
    )

    alphaAnimation
}

@Composable
private fun ImageAndProgress(
    progress: Float,
    image: ImageAsset?,
    modifier: Modifier = Modifier,
) = With { theme ->
    val size = RoundedImageButtonAppearance.Large.size

    Box(
        modifier = modifier
            .requiredSize(size),
    ) {
        Crossfade(
            targetState = image,
            label = "CrossfadeExerciseImage",
        ) {
            AsyncImage(
                image = it,
                modifier = Modifier
                    .clip(CircleShape)
                    .size(size),
            )
        }

        GradientProgressIndicatorLarge(
            progress = progress,
            modifier = Modifier
                .size(size),
            color = theme.current.colorScheme.background,
        )
    }
}

// TODO: multiple states
@Composable
@Preview
fun PreviewTimedWorkoutView_Initial() {
    PreviewContainer {
        TimedWorkoutView(
            title = "Test",
            remaining = "00:00",
            elapsedTotal = "00:00",
            remainingTotal = "01:00",
            playButtonState = ButtonState.Visible(),
            pauseButtonState = ButtonState.Hidden,
            stopButtonState = ButtonState.Hidden,
            progress = 0f,
            exerciseImage = null,
            backgroundColor = null,
        )
    }
}

@Composable
@Preview
fun PreviewTimedWorkoutView_Running() {
    PreviewContainer { theme ->
        TimedWorkoutView(
            title = "Test",
            remaining = "00:15",
            elapsedTotal = "00:00",
            remainingTotal = "01:00",
            playButtonState = ButtonState.Hidden,
            pauseButtonState = ButtonState.Visible(),
            stopButtonState = ButtonState.Visible(),
            progress = 0.5f,
            exerciseImage = ImageAsset("logo.png"),
            backgroundColor = theme.current.colorScheme.primary,
        )
    }
}

@Composable
@Preview
fun PreviewTimedWorkoutView_Finished() {
    PreviewContainer {
        TimedWorkoutView(
            title = "Test",
            remaining = "00:00",
            elapsedTotal = "01:00",
            remainingTotal = "00:00",
            playButtonState = ButtonState.Hidden,
            pauseButtonState = ButtonState.Visible(),
            stopButtonState = ButtonState.Visible(),
            progress = 1.0f,
            exerciseImage = ImageAsset("logo.png"),
        )
    }
}
