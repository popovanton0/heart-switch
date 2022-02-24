package com.popovanton0.heartswitch

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.Interaction
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.State
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.*

/**
 * Represents the colors used by a [HeartSwitch] in different states
 */
@Immutable
public data class HeartSwitchColors(
    public val checkedThumbColor: Color = Color.White,
    public val checkedTrackColor: Color = Color(0xffff708f),
    public val checkedTrackBorderColor: Color = Color(0xffff4e74),
    public val uncheckedThumbColor: Color = Color.White,
    public val uncheckedTrackColor: Color = Color.White,
    public val uncheckedTrackBorderColor: Color = Color(0xffd1d1d1),
)

/**
 * Heart-shaped switch. Similar to the Material implementation. Doesn't have a ripple.
 *
 * @param checked whether or not this component is checked
 * @param onCheckedChange callback to be invoked when [HeartSwitch] is being clicked,
 * therefore the change of checked state is requested.  If null, then this is passive
 * and relies entirely on a higher-level component to control the "checked" state.
 * @param modifier to be applied to the [HeartSwitch] layout
 * @param colors [HeartSwitchColors] that will be used to determine the color of the thumb and track
 * in different states
 * @param width of the [HeartSwitch]. Height will be determined by [HeartSwitch] automatically.
 * @param borderWidth it is not recommended to use large values (relative to the [width])
 * @param movementAnimSpec applied to movement of the track
 * @param colorsAnimSpec applied to all changes in color
 * @param thumb DO NOT set the size; it will be set automatically
 * @param enabled whether the component is enabled for touch input. Apply alpha yourself, if you
 * want to.
 * @param interactionSource the [MutableInteractionSource] representing the stream of
 * [Interaction]s for this [HeartSwitch]. You can create and pass in your own remembered
 * [MutableInteractionSource] if you want to observe [Interaction]s and customize the
 * appearance / behavior of this [HeartSwitch] in different [Interaction]s.
 */
@Composable
public fun HeartSwitch(
    checked: Boolean,
    onCheckedChange: ((Boolean) -> Unit)?,
    modifier: Modifier = Modifier,
    colors: HeartSwitchColors = HeartSwitchColors(),
    width: Dp = 40.dp,
    borderWidth: Dp = 2.dp,
    movementAnimSpec: AnimationSpec<Float> = tween(),
    colorsAnimSpec: AnimationSpec<Color> = tween(),
    thumb: @Composable (modifier: Modifier, color: State<Color>) -> Unit = { modifier, color ->
        Thumb(modifier, color)
    },
    enabled: Boolean = true,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
) {
    val animationProgress = animateFloatAsState(if (checked) 1f else 0f, movementAnimSpec)
    val toggleableModifier =
        if (onCheckedChange != null) {
            Modifier.toggleable(
                value = checked,
                onValueChange = onCheckedChange,
                enabled = enabled,
                role = Role.Switch,
                interactionSource = interactionSource,
                indication = null
            )
        } else {
            Modifier
        }

    val thumbColor = animateColorAsState(
        if (checked) colors.checkedThumbColor else colors.uncheckedThumbColor,
        animationSpec = colorsAnimSpec,
    )

    val heartColor = animateColorAsState(
        if (checked) colors.checkedTrackColor else colors.uncheckedTrackColor,
        animationSpec = colorsAnimSpec,
    )
    val borderColor = animateColorAsState(
        if (checked) colors.checkedTrackBorderColor else colors.uncheckedTrackBorderColor,
        animationSpec = colorsAnimSpec,
    )

    val height = width * HeartShape.WIDTH_TO_HEIGHT
    val thumbDiameter = width / HeartShape.CIRCLE_RADIUS_TO_WIDTH.dp * 2.dp - borderWidth * 2
    Box(Modifier.requiredSize(width, height)) {
        Track(
            modifier
                .requiredSize(width, height)
                .then(toggleableModifier),
            heartColor, borderColor, borderWidth
        )
        thumb(
            Modifier
                .requiredSize(thumbDiameter)
                .thumbOffset(width, height, borderWidth, thumbDiameter, animationProgress),
            thumbColor,
        )
    }
}

@Composable
private fun Track(
    modifier: Modifier,
    heartColor: State<Color>,
    borderColor: State<Color>,
    borderWidth: Dp
) = Box(
    modifier = modifier
        .background(heartColor.value, HeartShape())
        .border(borderWidth, borderColor.value, HeartShape())
)

@Composable
private fun Thumb(modifier: Modifier, color: State<Color>) = Box(
    modifier = modifier
        .shadow(6.dp, CircleShape)
        .background(color.value, CircleShape)
)

private fun Modifier.thumbOffset(
    width: Dp,
    height: Dp,
    borderWidth: Dp,
    thumbDiameter: Dp,
    animationProgress: State<Float>
): Modifier {
    val startOffset = DpOffset(borderWidth, borderWidth)
    val middleOffset = DpOffset(
        x = (width - thumbDiameter) / 2,
        y = height - thumbDiameter - borderWidth
    )
    val endOffset = DpOffset(
        x = width - thumbDiameter - borderWidth,
        y = borderWidth
    )
    return offset {
        // State read happens in the offset's scope. Only this scope will be recomposed, avoiding
        // recomposition and improving performance.
        // More: https://developer.android.com/jetpack/compose/phases#phase2-layout
        val progress = animationProgress.value
        val fraction =
            if (progress <= 0.5f) progress * 2
            else (progress - 0.5f) * 2

        val dpOffset =
            if (progress <= 0.5f) {
                lerp(startOffset, middleOffset, fraction)
            } else {
                lerp(middleOffset, endOffset, fraction)
            }

        IntOffset(
            x = dpOffset.x.roundToPx(),
            y = dpOffset.y.roundToPx(),
        )
    }
}
