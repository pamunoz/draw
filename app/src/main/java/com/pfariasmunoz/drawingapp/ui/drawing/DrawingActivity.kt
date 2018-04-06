package com.pfariasmunoz.drawingapp.ui.drawing

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.pfariasmunoz.drawingapp.R
import kotlinx.android.synthetic.main.activity_drawing.*

class DrawingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_drawing)

        btn_save_drawing.setOnClickListener({
            simpleDrawingView1.clear()
            //Log.i("DrawingActivity", "${simpleDrawingView1.mBitmap}")
        })
    }
}
