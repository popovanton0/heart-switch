package com.popovanton0.heartswitch.demo

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.popovanton0.heartswitch.HeartShape
import com.popovanton0.heartswitch.HeartSwitch

@Preview
@Composable
public fun HeartShapeConfig() = Column(Modifier.padding(horizontal = 12.dp)) {
    var showReactScreenshot by rememberSaveable(null) { mutableStateOf(false) }
    var cubicPoint1X by rememberSaveable(null) { mutableStateOf(0f) }
    var cubicPoint1Y by rememberSaveable(null) { mutableStateOf(0.9359191f) }
    var cubicPoint2X by rememberSaveable(null) { mutableStateOf(0.6977914f) }
    var cubicPoint2Y by rememberSaveable(null) { mutableStateOf(1.232277f) }

    Box {
        Box(
            modifier = Modifier
                .requiredWidth(300.dp)
                .background(
                    Color.Red, HeartShape(
                        cubicPoint1 = Offset(cubicPoint1X, cubicPoint1Y),
                        cubicPoint2 = Offset(cubicPoint2X, cubicPoint2Y)
                    )
                )
        )
        Image(
            modifier = Modifier
                .requiredWidth(300.dp)
                .alpha(if (showReactScreenshot) 0.6f else 0f),
            contentScale = ContentScale.FillWidth,
            painter = painterResource(id = R.drawable.react_heart),
            contentDescription = null
        )
    }

    Slider(label = "point 1 x", value = cubicPoint1X, valueRange = 0f..1f) { cubicPoint1X = it }
    Slider(label = "point 1 y", value = cubicPoint1Y, valueRange = 0f..3f) { cubicPoint1Y = it }
    Slider(label = "point 2 x", value = cubicPoint2X, valueRange = 0f..1f) { cubicPoint2X = it }
    Slider(label = "point 2 y", value = cubicPoint2Y, valueRange = 0f..3f) { cubicPoint2Y = it }

    Row(
        Modifier
            .fillMaxWidth()
            .clickable { showReactScreenshot = !showReactScreenshot }
            .padding(12.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = "Show React component screenshot")
        HeartSwitch(
            checked = showReactScreenshot,
            onCheckedChange = { showReactScreenshot = it }
        )
    }
}

@Composable
private fun Slider(
    label: String,
    value: Float,
    valueRange: ClosedFloatingPointRange<Float> = 0f..1f,
    onValueChange: (Float) -> Unit,
) = Column {
    Text(text = "$label: $value")
    androidx.compose.material.Slider(
        value = value,
        onValueChange = onValueChange,
        valueRange = valueRange
    )
}
