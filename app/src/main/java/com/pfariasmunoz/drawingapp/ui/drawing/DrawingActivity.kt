package com.pfariasmunoz.drawingapp.ui.drawing

import android.app.ActionBar
import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.pfariasmunoz.drawingapp.R
import com.pfariasmunoz.drawingapp.di.Injector
import com.pfariasmunoz.drawingapp.util.CURRENT_USER_ID
import com.pfariasmunoz.drawingapp.util.preferences
import kotlinx.android.synthetic.main.activity_draw.*
import kotlinx.android.synthetic.main.activity_drawing.*
import java.io.FileNotFoundException

class DrawingActivity : AppCompatActivity(), DrawingContract.View {


    val presenter: DrawingPresenter

    var source: Uri? = null

    init {
        presenter = Injector.get().drawingPresenter()
    }

    companion object {
        private const val RQS_IMAGE1 = 1
    }

    override val currentUserId: String
        get() = preferences.getString(CURRENT_USER_ID, "")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_drawing)
        //setSupportActionBar(drawing_toolbar)
        supportActionBar?.apply {
            displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
            setCustomView(R.layout.abs_layout)
            setDisplayHomeAsUpEnabled(true)
        }
        with(presenter) {
            setupView(this@DrawingActivity)
        }
        setupListeners()
    }

    override fun draw(bitmap: Bitmap) {
        simpleDrawingView1.mBitmap = bitmap
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        var tempBitmap: Bitmap
        if (resultCode == Activity.RESULT_OK) {
            when(requestCode) {
                RQS_IMAGE1 -> {
                    source = data?.data
                    try {
                        tempBitmap = BitmapFactory.decodeStream(contentResolver.openInputStream(source))

                        val config: Bitmap.Config = if (tempBitmap.config != null) {
                            tempBitmap.config
                        } else {
                            Bitmap.Config.ARGB_8888
                        }

                        //bitmapMaster is Mutable bitmap
                        simpleDrawingView1.mBitmap = Bitmap.createBitmap(
                                tempBitmap.width,
                                tempBitmap.height,
                                config)

                        simpleDrawingView1.mCanvas = Canvas(simpleDrawingView1.mBitmap).apply {
                            drawBitmap(tempBitmap, 0f, 0f, null)
                        }

                    } catch (e: FileNotFoundException) {
                        e.printStackTrace()
                    }
                }
            }
        }

    }

    private fun setupListeners() {
        btn_load_drawing.setOnClickListener({
            val intent = Intent(Intent.ACTION_PICK,
                    android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(intent, RQS_IMAGE1)
        })

        btn_save_drawing.setOnClickListener({
            if (simpleDrawingView1.mBitmap != null) {
                presenter.saveBitmap(simpleDrawingView1.mBitmap)
            }
        })
        btn_clear_drawing.setOnClickListener({
            simpleDrawingView1.clear()
        })
    }

    /*
Project position on ImageView to position on Bitmap draw on it
 */

    private fun drawOnProjectedBitMap(startX: Float, startY: Float, stopX: Float, stopY: Float) {
        if (stopX < 0 || stopY < 0 || stopX > simpleDrawingView1.width || stopY > simpleDrawingView1.height) {
            //outside ImageView
            return
        } else {

            val ratioWidth = simpleDrawingView1.mBitmap!!.width.toFloat() / simpleDrawingView1.width.toFloat()
            val ratioHeight = simpleDrawingView1.mBitmap.height.toFloat() / simpleDrawingView1.height.toFloat()

            simpleDrawingView1.mCanvas.drawLine(
                    startX * ratioWidth,
                    startY * ratioHeight,
                    stopX * ratioWidth,
                    stopY * ratioHeight,
                    simpleDrawingView1.mBitmapPaint)
            result.invalidate()
        }
    }

}
