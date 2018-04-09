package com.pfariasmunoz.drawingapp.ui.drawing

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.support.v7.app.AppCompatActivity
import android.view.MotionEvent
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import com.pfariasmunoz.drawingapp.R
import kotlinx.android.synthetic.main.activity_draw.*

import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException


class DrawActivity : AppCompatActivity(){

    companion object {
        private const val RQS_IMAGE1 = 1
    }
    var source: Uri? = null
    private var bitmapMaster: Bitmap? = null
    private lateinit var canvasMaster: Canvas

    private var prvX: Int = 0
    private var prvY:Int = 0

    private lateinit var paintDraw: Paint

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_draw)

        paintDraw = Paint().apply {
            style = Paint.Style.FILL
            color = Color.WHITE
            strokeWidth = 10f
        }
        loadimage.setOnClickListener({

            val intent = Intent(Intent.ACTION_PICK,
                    android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(intent, RQS_IMAGE1)
        })

        saveimage.setOnClickListener( {
            if (bitmapMaster != null) {
                saveBitmap(bitmapMaster!!)
            }
        })

        result.setOnTouchListener({
            v: View, event: MotionEvent ->
            val action = event.action
            val x = event.x.toInt()
            val y = event.y.toInt()
            when (action) {
                MotionEvent.ACTION_DOWN -> {
                    prvX = x
                    prvY = y
                    drawOnProjectedBitMap(v as ImageView, bitmapMaster, prvX.toFloat(), prvY.toFloat(), x.toFloat(), y.toFloat())
                }
                MotionEvent.ACTION_MOVE -> {
                    drawOnProjectedBitMap(v as ImageView, bitmapMaster, prvX.toFloat(), prvY.toFloat(), x.toFloat(), y.toFloat())
                    prvX = x
                    prvY = y
                }
                MotionEvent.ACTION_UP -> drawOnProjectedBitMap(v as ImageView, bitmapMaster, prvX.toFloat(), prvY.toFloat(), x.toFloat(), y.toFloat())
            }
            /*
            * Return 'true' to indicate that the event have been consumed.
            * If auto-generated 'false', your code can detect ACTION_DOWN only,
            * cannot detect ACTION_MOVE and ACTION_UP.
            */
            true
        })

    }

    /*
    Project position on ImageView to position on Bitmap draw on it
     */

    private fun drawOnProjectedBitMap(view: ImageView, bm: Bitmap?,
                                      startX: Float, startY: Float, stopX: Float, stopY: Float) {
        if (stopX < 0 || stopY < 0 || stopX > view.width || stopY > view.height) {
            //outside ImageView
            return
        } else {

            val ratioWidth = bm!!.width.toFloat() / view.width.toFloat()
            val ratioHeight = bm.height.toFloat() / view.height.toFloat()

            canvasMaster.drawLine(
                    startX * ratioWidth,
                    startY * ratioHeight,
                    stopX * ratioWidth,
                    stopY * ratioHeight,
                    paintDraw)
            result.invalidate()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        super.onActivityResult(requestCode, resultCode, data)

        val tempBitmap: Bitmap

        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                RQS_IMAGE1 -> {
                    source = data.data

                    try {
                        //tempBitmap is Immutable bitmap,
                        //cannot be passed to Canvas constructor
                        tempBitmap = BitmapFactory.decodeStream(
                                contentResolver.openInputStream(source!!))

                        val config: Bitmap.Config = if (tempBitmap.config != null) {
                            tempBitmap.config
                        } else {
                            Bitmap.Config.ARGB_8888
                        }

                        //bitmapMaster is Mutable bitmap
                        bitmapMaster = Bitmap.createBitmap(
                                tempBitmap.width,
                                tempBitmap.height,
                                config)

                        canvasMaster = Canvas(bitmapMaster).apply {
                            drawBitmap(tempBitmap, 0f, 0f, null)
                        }
                        result.setImageBitmap(bitmapMaster)
                    } catch (e: FileNotFoundException) {
                        e.printStackTrace()
                    }

                }
            }
        }
    }

    private fun saveBitmap(bm: Bitmap) {
        val file = Environment.getExternalStorageDirectory()
        val newFile = File(file, "test.jpg")

        try {
            val fileOutputStream = FileOutputStream(newFile)
            bm.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream)
            fileOutputStream.flush()
            fileOutputStream.close()
            Toast.makeText(this@DrawActivity,
                    "Save Bitmap: " + fileOutputStream.toString(),
                    Toast.LENGTH_LONG).show()
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
            Toast.makeText(this@DrawActivity,
                    "Something wrong: " + e.message,
                    Toast.LENGTH_LONG).show()
        } catch (e: IOException) {
            e.printStackTrace()
            Toast.makeText(this@DrawActivity,
                    "Something wrong: " + e.message,
                    Toast.LENGTH_LONG).show()
        }

    }

}