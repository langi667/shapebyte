package de.stefan.lang.shapebyte.android.workout.item.ui

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import de.stefan.lang.shapebyte.android.designsystem.ui.components.Title
import de.stefan.lang.shapebyte.designsystem.ui.ThemeProvider
import de.stefan.lang.shapebyte.features.workout.data.ItemSet
import de.stefan.lang.shapebyte.features.workout.ui.CountdownItemSetsViewModel
import kotlin.time.Duration.Companion.seconds

@Composable
fun WithTheme(content: @Composable (theme: ThemeProvider) -> Unit) {
    val theme: ThemeProvider = remember { ThemeProvider() }
    content(theme)
}

@Composable
fun CountdownItemSetsView(viewModel: CountdownItemSetsViewModel, modifier: Modifier = Modifier) {
    val state = viewModel.state.collectAsStateWithLifecycle()

    val scale by animateFloatAsState(
        targetValue = state.value.scale,
        animationSpec = tween(durationMillis = state.value.animationDuration),
        label = "scale",
    )

    val alpha by animateFloatAsState(
        targetValue = state.value.alpha,
        animationSpec = tween(durationMillis = state.value.animationDuration),
        label = "scale",
    )

    LaunchedEffect(key1 = Unit) {
        viewModel.start(
            listOf(
                ItemSet.Timed(1.seconds),
                ItemSet.Timed(1.seconds),
                ItemSet.Timed(1.seconds),
                ItemSet.Timed(1.seconds),
                ItemSet.Timed(1.seconds),
            ),
        )
    }

    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Title(
            modifier = Modifier
                .scale(scale)
                .alpha(alpha),
            text = state.value.countdownText,
        )
    }
}
