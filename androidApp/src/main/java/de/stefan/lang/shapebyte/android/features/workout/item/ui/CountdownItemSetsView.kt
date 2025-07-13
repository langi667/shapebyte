package de.stefan.lang.shapebyte.android.features.workout.item.ui

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import de.stefan.lang.shapebyte.android.designsystem.ui.components.text.DisplayLarge
import de.stefan.lang.shapebyte.features.workout.api.countdown.CountdownItemSetsUIIntent
import de.stefan.lang.shapebyte.features.workout.api.countdown.CountdownItemSetsViewModel
import de.stefan.lang.shapebyte.features.workout.api.item.ItemSet

@Composable
fun CountdownItemSetsView(viewModel: CountdownItemSetsViewModel, modifier: Modifier = Modifier) {
    val state = viewModel.state.collectAsStateWithLifecycle()

    val scale by animateFloatAsState(
        targetValue = state.value.data.scale,
        animationSpec = tween(durationMillis = state.value.data.animationDuration),
        label = "scale",
    )

    val alpha by animateFloatAsState(
        targetValue = state.value.data.alpha,
        animationSpec = tween(durationMillis = state.value.data.animationDuration),
        label = "scale",
    )

    LaunchedEffect(key1 = Unit) {
        viewModel.intent(
            CountdownItemSetsUIIntent.Start(
                listOf(
                    ItemSet.Timed.Seconds(1),
                    ItemSet.Timed.Seconds(1),
                    ItemSet.Timed.Seconds(1),
                    ItemSet.Timed.Seconds(1),
                    ItemSet.Timed.Seconds(1),
                ),
            ),
        )
    }

    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        DisplayLarge(
            modifier = Modifier
                .scale(scale)
                .alpha(alpha),
            text = state.value.data.countdownText,
        )
    }
}
