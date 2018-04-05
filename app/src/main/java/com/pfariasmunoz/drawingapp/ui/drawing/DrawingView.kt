package com.pfariasmunoz.drawingapp.ui.drawing

import android.content.Context
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View


class DrawingView(context: Context, attrs: AttributeSet): View(context, attrs) {
    
    private val drawPaint: Paint

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

}