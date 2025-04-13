package de.stefan.lang.shapebyte.android.shared.progress.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.progressSemantics
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import de.stefan.lang.shapebyte.android.designsystem.ui.ThemeData
import de.stefan.lang.shapebyte.android.shared.preview.ui.PreviewContainer

private const val START_ANGLE = 270.0f
private const val FULL_CIRCLE_DEGREES = 360.0f
private const val DEFAULT_ROTATION = FULL_CIRCLE_DEGREES - START_ANGLE

// TODO: Appearance class
@Composable
fun GradientProgressIndicatorLarge(
    progress: Float,
    modifier: Modifier = Modifier,
    color: Color? = null,
) {
    val gradientColor = color ?: MaterialTheme.colorScheme.primary
    GradientProgressIndicator(
        progress = progress,
        gradientStart = gradientColor.copy(alpha = 0.3f),
        gradientEnd = gradientColor,
        trackColor = Color.Transparent,
        strokeWidth = ThemeData.dimensions.tiny.dp,
        modifier = modifier,
    )
}

@Composable
fun GradientProgressIndicator(
    progress: Float,
    gradientStart: Color,
    gradientEnd: Color,
    trackColor: Color,
    strokeWidth: Dp,
    modifier: Modifier = Modifier,
) {
    val stroke = with(LocalDensity.current) {
        Stroke(width = strokeWidth.toPx(), cap = StrokeCap.Butt)
    }
    Canvas(
        modifier
            .progressSemantics(progress),
    ) {
        // Start at 12 o'clock
        val sweep = progress * FULL_CIRCLE_DEGREES
        drawDeterminateCircularIndicator(START_ANGLE, FULL_CIRCLE_DEGREES, trackColor, stroke)
        drawCircularIndicator(
            startAngle = START_ANGLE,
            sweep = sweep,
            gradientStart = gradientStart,
            gradientEnd = gradientEnd,
            stroke = stroke,
        )
    }
}

private fun DrawScope.drawDeterminateCircularIndicator(
    startAngle: Float,
    sweep: Float,
    color: Color,
    stroke: Stroke,
) = drawCircularIndicator(startAngle, sweep, color, stroke)

private fun DrawScope.drawCircularIndicator(
    startAngle: Float,
    sweep: Float,
    color: Color,
    stroke: Stroke,
) {
    // To draw this circle we need a rect with edges that line up with the midpoint of the stroke.
    // To do this we need to remove half the stroke width from the total diameter for both sides.
    val diameterOffset = stroke.width / 2
    val arcDimen = size.width - 2 * diameterOffset
    drawArc(
        color = color,
        startAngle = startAngle,
        sweepAngle = sweep,
        useCenter = false,
        topLeft = Offset(diameterOffset, diameterOffset),
        size = Size(arcDimen, arcDimen),
        style = stroke,
    )
}

private fun DrawScope.drawCircularIndicator(
    startAngle: Float,
    sweep: Float,
    gradientStart: Color,
    gradientEnd: Color,
    stroke: Stroke,
) {
    // To draw this circle we need a rect with edges that line up with the midpoint of the stroke.
    // To do this we need to remove half the stroke width from the total diameter for both sides.
    val diameterOffset = stroke.width / 2
    val arcDimen = size.width - 2 * diameterOffset
    rotate(degrees = -DEFAULT_ROTATION) {
        drawArc(
            brush = Brush.sweepGradient(
                colorStops = listOf(
                    0.0f to gradientStart,
                    sweep / FULL_CIRCLE_DEGREES to gradientEnd,
                ).toTypedArray(),
            ),
            startAngle = startAngle + DEFAULT_ROTATION,
            sweepAngle = sweep,
            useCenter = false,
            topLeft = Offset(diameterOffset, diameterOffset),
            size = Size(arcDimen, arcDimen),
            style = stroke,
        )
    }
}

@Preview
@Composable
fun PreviewGradientProgressRing() {
    PreviewContainer {
        GradientProgressIndicatorLarge(
            progress = 0.5f,
            modifier = Modifier.size(128.dp),
        )
    }
}
