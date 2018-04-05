package com.pfariasmunoz.drawingapp.ui.drawing

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View



class DrawingView(context: Context, attrs: AttributeSet): View(context, attrs) {

    private val drawPaint: Paint
    private val path = Path()

    init {

        isFocusable = true
        isFocusableInTouchMode = true
        drawPaint = Paint().apply {
            color = Color.BLACK
            isAntiAlias = true
            strokeWidth = 5f
            style = Paint.Style.STROKE
            strokeJoin = Paint.Join.ROUND
            strokeCap = Paint.Cap.ROUND
        }
    }

    override fun onDraw(canvas: Canvas?) {
        canvas?.drawPath(path, drawPaint)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        val touchX = event?.x ?: 0f
        val touchY = event?.y ?: 0f

        when(event?.action) {
            MotionEvent.ACTION_DOWN -> path.moveTo(touchX, touchY)
            MotionEvent.ACTION_MOVE -> path.lineTo(touchX, touchY)
        }

        postInvalidate()
        return true
    }

}