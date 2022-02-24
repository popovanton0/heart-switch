package com.popovanton0.heartswitch.demo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.datasource.LoremIpsum
import androidx.compose.ui.unit.dp
import com.popovanton0.heartswitch.HeartSwitch
import com.popovanton0.heartswitch.HeartSwitchColors
import com.popovanton0.heartswitch.demo.ui.theme.HeartSwitchTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HeartSwitchTheme {
                Surface(color = MaterialTheme.colors.background) {
                    Content()
                }
            }
        }
    }
}

@Composable
private fun Content() = Column(
    Modifier
        .fillMaxSize()
        .verticalScroll(rememberScrollState())
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(120.dp),
        contentAlignment = Alignment.Center
    ) { Hero() }
    ListSample()
    Text(
        modifier = Modifier.padding(12.dp),
        text = "Heart Shape Configurator",
        style = MaterialTheme.typography.h4
    )
    HeartShapeConfig()
}

@Preview
@Composable
private fun Hero() {
    var state by remember { mutableStateOf(true) }
    HeartSwitch(
        checked = state,
        onCheckedChange = { state = it },
        width = 160.dp,
        borderWidth = 6.dp
    )
}

@Preview
@Composable
private fun BasicUsage() {
    var state by remember { mutableStateOf(true) }
    HeartSwitch(
        checked = state,
        onCheckedChange = { state = it },
    )
}

@Preview()
@Composable
private fun AdvancedUsage() {
    var state by remember { mutableStateOf(true) }
    HeartSwitch(
        checked = state,
        onCheckedChange = { state = it },
        modifier = Modifier,
        colors = HeartSwitchColors(
            checkedTrackColor = Color(0xFFE91E63),
            checkedTrackBorderColor = Color(0xFFC2185B),
            uncheckedTrackBorderColor = Color(0xFFBDBDBD)
        ),
        width = 34.dp,
        borderWidth = 2.1.dp,
        movementAnimSpec = spring(stiffness = Spring.StiffnessMediumLow),
        colorsAnimSpec = spring(stiffness = Spring.StiffnessMediumLow),
        thumb = { modifier, color ->
            Box(modifier = modifier
                    .shadow(12.dp, CircleShape)
                    .background(color.value, CircleShape))
        },
        enabled = true,
        interactionSource = remember { MutableInteractionSource() },
    )
}

@Composable
private fun ListSample() = Column {
    repeat(5) {
        var state by remember { mutableStateOf(true) }
        Row(
            Modifier
                .fillMaxWidth()
                .clickable { state = !state }
                .padding(12.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = LoremIpsum(4).values.first())
            HeartSwitch(
                checked = state,
                onCheckedChange = { state = it })
        }
    }
}
