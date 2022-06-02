package com.example.pomotodoro_compose.components.pomodoro

import android.util.Log
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import com.example.pomotodoro_compose.ui.theme.Blue500
import com.example.pomotodoro_compose.ui.theme.Purple500
import kotlinx.coroutines.isActive


@Composable
fun DrawCircles() {
    val startAngle = 0f
    val angle1 = remember { Animatable(initialValue = 0f) }
    val angle2 = remember { Animatable(initialValue = 0f) }
    val density = LocalDensity.current.density
    val boxSize = 220f
    val circleStrokeWidth = 43f
    val outerCircleSize = (boxSize * density) - (circleStrokeWidth / 2 * density)

    Canvas(
        modifier = Modifier
            .size(Dp(boxSize))
    ) {
        drawRect(color = Color.Transparent)
        rotate(-90f) {
            val offset = circleStrokeWidth / 2
            drawArc(
                Brush.sweepGradient(listOf(Blue500, Blue500)),
                startAngle,
                angle1.value,
                false,
                topLeft = Offset(offset, offset),
                size = Size(outerCircleSize, outerCircleSize),
                style = Stroke(circleStrokeWidth, 0f)
            )
                drawArc(
                    Brush.sweepGradient(listOf(Color.LightGray, Color.LightGray)),
                    startAngle,
                    angle2.value,
                    false,
                    topLeft = Offset(offset, offset),
                    size = Size(outerCircleSize, outerCircleSize),
                    style = Stroke(circleStrokeWidth, 0f)
                )
        }
    }

    LaunchedEffect("Square") {
        while (isActive) {
            Log.d("NGVL", "Here!")
            angle1.animateTo(
                targetValue = 360f,
                animationSpec = tween(
                    durationMillis = 0,
                    easing = FastOutSlowInEasing,
                )
            )
            angle2.animateTo(
                targetValue = 64f,
                animationSpec = tween(
                    durationMillis = 0,
                    easing = LinearOutSlowInEasing,
                )
            )

//            angle1.snapTo(0f)
//            angle2.snapTo(0f)
        }
    }
}

