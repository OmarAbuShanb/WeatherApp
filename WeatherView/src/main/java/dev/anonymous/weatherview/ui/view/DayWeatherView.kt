package dev.anonymous.weatherview.ui.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.graphics.Rect
import android.util.AttributeSet
import android.view.View
import dev.anonymous.weatherview.ui.utils.PositionX
import dev.anonymous.weatherview.ui.utils.PositionY
import dev.anonymous.weatherview.ui.utils.ScreenUtils

class DayWeatherView(context: Context, attrs: AttributeSet) : View(context, attrs) {
    private val linePaint = Paint()
    private val linePath = Path()

    private val circlePaint = Paint()
    private val circlePath = Path()

    private val textPaint = Paint()
    private var temperature = ""
    private var textX = 0f
    private var textY = 0f

    init {
        linePaint.color = Color.RED
        linePaint.style = Paint.Style.STROKE
        linePaint.strokeWidth = LINE_STROKE_WIDTH

        textPaint.color = Color.WHITE
        textPaint.textSize = ScreenUtils.spToPixel(13f, context)

        circlePaint.color = Color.WHITE
        circlePaint.style = Paint.Style.FILL
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        // draw first and last line and center circle
        canvas.drawPath(linePath, linePaint)
        // draw smaller circle in center circle
        canvas.drawPath(circlePath, circlePaint)
        // draw text above center circle
        canvas.drawText(temperature, textX, textY, textPaint)
    }

    fun setPointsPosition(
        temperature: String,
        firstPoint: Pair<PositionX, PositionY>?,
        centerPoint: Pair<PositionX, PositionY>,
        lastPoint: Pair<PositionX, PositionY>?
    ) {
        this.temperature = temperature
        addTemperatureText(centerPoint)

        if (firstPoint != null) {
            addFirstLine(firstPoint, centerPoint)
        }
        addCenterCircles(centerPoint)
        if (lastPoint != null) {
            addLastLine(centerPoint, lastPoint)
        }
        // rebuild the view
        invalidate()
    }

    private fun addTemperatureText(centerPoint: Pair<PositionX, PositionY>) {
        val rect = Rect()
        textPaint.getTextBounds(temperature, 0, temperature.length, rect)
        val textWidth = rect.width()
        textX = centerPoint.first - (textWidth / 2).toFloat() - 4f
        textY = centerPoint.second - (CIRCLE_RADIOS / 2) - 30f
    }

    private fun addFirstLine(
        firstPoint: Pair<PositionX, PositionY>,
        centerPoint: Pair<PositionX, PositionY>
    ) {
        // Set Start Point
        linePath.moveTo(
            firstPoint.first.toFloat(),
            firstPoint.second.toFloat()
        )
        // drawing a line that ends in the center
        linePath.lineTo(
            centerPoint.first.toFloat(),
            centerPoint.second.toFloat()
        )
    }

    private fun addCenterCircles(centerPoint: Pair<PositionX, PositionY>) {
        // center circle
        linePath.addCircle(
            centerPoint.first.toFloat(),
            centerPoint.second.toFloat(),
            CIRCLE_RADIOS,
            Path.Direction.CW
        )
        // a smaller circle over the center circle
        circlePath.addCircle(
            centerPoint.first.toFloat(),
            centerPoint.second.toFloat(),
            SMALLER_CIRCLE_RADIOS,
            Path.Direction.CW
        )
    }

    private fun addLastLine(
        centerPoint: Pair<PositionX, PositionY>,
        lastPoint: Pair<PositionX, PositionY>
    ) {
        // Set Start Point
        linePath.moveTo(
            centerPoint.first.toFloat(),
            centerPoint.second.toFloat()
        )
        // drawing a line that ends in the center next item
        linePath.lineTo(
            lastPoint.first.toFloat(),
            lastPoint.second.toFloat()
        )
    }

    companion object {
        private const val TAG = "DayWeatherView"
        private const val LINE_STROKE_WIDTH = 4f
        private const val CIRCLE_RADIOS = 12f

        // 10f =  12f - (4f / 2)
        private const val SMALLER_CIRCLE_RADIOS = CIRCLE_RADIOS - (LINE_STROKE_WIDTH / 2)
    }
}