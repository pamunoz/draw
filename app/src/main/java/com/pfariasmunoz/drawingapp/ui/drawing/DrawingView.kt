package com.pfariasmunoz.drawingapp.ui.drawing

import android.content.ContentValues
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException

/**
 * This activity is in charge of the drawing actions.
 */
class DrawingView(context: Context, attrs: AttributeSet?): View(context, attrs) {

    /**
     * The bitmap to which the drawing is make
     */
    var mBitmap: Bitmap? = null
    lateinit var mCanvas: Canvas
    private val mPath: Path
    val mBitmapPaint: Paint
    private val mDrawPaint: Paint
    private var mXtouch = 0f
    private var mYtouch = 0f

    companion object {
        private val TOUCH_TOLERANCE = 4F
    }

    init {
        isDrawingCacheEnabled = true
        mPath = Path()
        mBitmapPaint = Paint(Paint.DITHER_FLAG).apply {
            setBackgroundColor(Color.WHITE)
        }
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
        val unmutableBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)
        mBitmap = unmutableBitmap.copy(Bitmap.Config.ARGB_8888, true)
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
        val unmutableBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        mBitmap = unmutableBitmap.copy(Bitmap.Config.ARGB_8888, true)
        mBitmap?.eraseColor(Color.WHITE)
        invalidate()
        System.gc()
    }

    fun saveBitmap(picName: String) {
        var fos: FileOutputStream? = null
        try {
            fos = context.openFileOutput(picName, Context.MODE_PRIVATE)
            mBitmap?.compress(Bitmap.CompressFormat.PNG, 100, fos)
        } catch (e: FileNotFoundException) {
            Log.d(ContentValues.TAG, "file not found")
            e.printStackTrace()
        } catch (e: IOException) {
            Log.d(ContentValues.TAG, "io exception")
            e.printStackTrace()
        } finally {
            fos?.close()
        }
    }

    fun loadBitmap(picName: String) {
        var b: Bitmap? = null
        var fis: FileInputStream? = null
        try {
            fis = context.openFileInput(picName)
            b = BitmapFactory.decodeStream(fis)
        } catch (e: FileNotFoundException) {
            Log.d(ContentValues.TAG, "file not found")
            e.printStackTrace()
        } catch (e: IOException) {
            Log.d(ContentValues.TAG, "io exception")
            e.printStackTrace()
        } finally {
            fis?.close()
        }
        mBitmap = b
    }
}