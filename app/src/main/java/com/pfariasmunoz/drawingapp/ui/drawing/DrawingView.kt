package com.pfariasmunoz.drawingapp.ui.drawing

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View



class DrawingView(context: Context, attrs: AttributeSet?): View(context, attrs) {

    lateinit var mBitmap: Bitmap
    lateinit var mCanvas: Canvas
    private val mPath: Path
    private val mBitmapPaint: Paint
    private val mDrawPaint: Paint
    private var mXtouch = 0f
    private var mYtouch = 0f

    companion object {
        private val TOUCH_TOLERANCE = 4F
    }

    init {
        mPath = Path()
        mBitmapPaint = Paint(Paint.DITHER_FLAG)
        isFocusable = true
        isFocusableInTouchMode = true
        mDrawPaint = Paint().apply {
            color = Color.BLACK
            isAntiAlias = true
            strokeWidth = 9f
            style = Paint.Style.STROKE
            strokeJoin = Paint.Join.ROUND
            strokeCap = Paint.Cap.ROUND
        }
    }

    override fun onDraw(canvas: Canvas?) {
        canvas?.drawBitmap(mBitmap, 0f, 0f, mBitmapPaint)
        canvas?.drawPath(mPath, mDrawPaint)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        mBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)
        mCanvas = Canvas(mBitmap)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        val touchX = event?.x ?: 0f
        val touchY = event?.y ?: 0f

        when(event?.action) {
            MotionEvent.ACTION_DOWN -> touchStart(touchX, touchY)
            MotionEvent.ACTION_MOVE -> touchMove(touchX, touchY)
            MotionEvent.ACTION_UP -> touchUp()
        }

        postInvalidate()
        return true
    }

    private fun touchStart(xpos: Float, ypos: Float) {
        mPath.apply {
            reset()
            moveTo(xpos, ypos)
        }
        mXtouch = xpos
        mYtouch = ypos
        invalidate()
    }

    private fun touchMove(xpos: Float, ypos: Float) {
        val dx = Math.abs(xpos - mXtouch)
        val dy = Math.abs(ypos - mYtouch)
        if (dx >= TOUCH_TOLERANCE || dy >= TOUCH_TOLERANCE) {
            mPath.quadTo(mXtouch, mYtouch, (xpos + mXtouch)/ 2, (ypos + mYtouch)/2)
            mXtouch = xpos
            mYtouch = ypos
        }
        invalidate()
    }
    private fun touchUp() {
        mPath.lineTo(mXtouch, mYtouch)
        // commit the path to our offscreen
        mCanvas.drawPath(mPath, mDrawPaint)
        // kill this so we don't double draw
        mPath.reset()
        invalidate()
    }

    fun clear() {
        mBitmap.eraseColor(Color.GREEN)
        invalidate()
        System.gc()
    }

}