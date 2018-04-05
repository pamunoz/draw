package com.pfariasmunoz.drawingapp.ui.drawing

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Point
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View



class DrawingView(context: Context, attrs: AttributeSet): View(context, attrs) {

    private val drawPaint: Paint
    private val circlePoints: MutableList<Point>

    init {
        circlePoints = mutableListOf()
        isFocusable = true
        isFocusableInTouchMode = true
        drawPaint = Paint().apply {
            color = Color.BLACK
            isAntiAlias = true
            strokeWidth = 5f
            style = Paint.Style.FILL
            strokeJoin = Paint.Join.ROUND
            strokeCap = Paint.Cap.ROUND
        }
    }

    override fun onDraw(canvas: Canvas?) {
        canvas?.apply {
            circlePoints.forEach {
                canvas.drawCircle(it.x.toFloat(), it.y.toFloat(), 5f, drawPaint) }
        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        val touchX = event?.x
        val touchY = event?.y
        circlePoints.add(Point(Math.round(touchX!!), Math.round(touchY!!)))
        // indicate view should be redrawn
        postInvalidate()
        return true
    }

}