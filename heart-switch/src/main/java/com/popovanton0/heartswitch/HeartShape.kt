package com.popovanton0.heartswitch

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathOperation
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection

@Immutable
public class HeartShape(
    private val cubicPoint1: Offset,
    private val cubicPoint2: Offset,
) : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline = Outline.Generic(createPath(size))

    private fun createPath(size: Size) = Path().apply {
        val circleRadius = size.width / CIRCLE_RADIUS_TO_WIDTH
        val circleDiameter = circleRadius * 2

        // left circle
        val leftCircle = Path().apply {
            addOval(Rect(center = Offset(circleRadius, circleRadius), radius = circleRadius))
        }

        // right circle
        val rightCircleCenter = circleDiameter + circleRadius * 0.5f
        val rightCircle = Path().apply {
            addOval(Rect(center = Offset(rightCircleCenter, circleRadius), radius = circleRadius))
        }

        // x of intersection of 2 circles
        val midpoint = (rightCircleCenter + circleRadius) / 2

        // bottom of the heart
        moveTo(0f, circleRadius)
        cubicTo(
            x1 = midpoint * cubicPoint1.x,
            y1 = circleDiameter * cubicPoint1.y,
            x2 = midpoint * cubicPoint2.x,
            y2 = circleDiameter * cubicPoint2.y,
            x3 = midpoint,
            y3 = circleDiameter * CIRCLE_DIAMETER_TO_HEIGHT,
        )
        cubicTo(
            x1 = midpoint * (2f - cubicPoint2.x),
            y1 = circleDiameter * cubicPoint2.y,
            x2 = midpoint * (2f - cubicPoint1.x),
            y2 = circleDiameter * cubicPoint1.y,
            x3 = midpoint * 2,
            y3 = circleRadius,
        )

        op(this, leftCircle, PathOperation.Union)
        op(this, rightCircle, PathOperation.Union)
        close()
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as HeartShape

        if (cubicPoint1 != other.cubicPoint1) return false
        if (cubicPoint2 != other.cubicPoint2) return false

        return true
    }

    override fun hashCode(): Int {
        var result = cubicPoint1.hashCode()
        result = 31 * result + cubicPoint2.hashCode()
        return result
    }

    public companion object {
        @Stable
        public operator fun invoke(): HeartShape = Default

        /**
         * Performance optimization: imitation of the default no-arguments constructor is actually
         * just a function call that returns cached [Default] instance of the [HeartShape].
         */
        private val Default = HeartShape(
            cubicPoint1 = Offset(x = 0f, y = 0.9359191f),
            cubicPoint2 = Offset(x = 0.6977914f, y = 1.232277f),
        )
        private const val CIRCLE_DIAMETER_TO_HEIGHT = 1.22f
        internal const val CIRCLE_RADIUS_TO_WIDTH = 3.5f
        internal const val WIDTH_TO_HEIGHT =
            1 / CIRCLE_RADIUS_TO_WIDTH * 2 * CIRCLE_DIAMETER_TO_HEIGHT
    }
}