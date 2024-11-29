package de.stefan.lang.shapebyte.android.shared.shapes.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun Arc(
    modifier: Modifier = Modifier,
    width: Dp = 50.dp,
    height: Dp = 20.dp,
    color: Color = MaterialTheme.colorScheme.background,
) {
    Canvas(
        modifier = modifier
            .width(width)
            .height(height),
    ) {
        drawQuadraticBezierCurve(color)
    }
}

fun DrawScope.drawQuadraticBezierCurve(color: Color) {
    val path = Path().apply {
        moveTo(0f, size.height) // Start point
        quadraticBezierTo(size.width / 2, size.height * -1, size.width, size.height)
    }
    drawPath(
        path = path,
        color = color,
    )
}

@Preview
@Composable
fun PreviewQuadraticBezierCurve() {
    Arc(width = 200.dp, height = 20.dp)
}
