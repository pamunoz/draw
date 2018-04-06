package com.pfariasmunoz.drawingapp.ui.drawing

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.pfariasmunoz.drawingapp.R
import kotlinx.android.synthetic.main.activity_drawing.*

class DrawingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val drawingView = DrawingView(this, null)
        setContentView(R.layout.activity_drawing)


        btn_save_drawing.setOnClickListener({


            Log.i("DrawingActivity", "${drawingView.drawingCache}")
            //Log.i("DrawingActivity", "${drawingView.mBitmap}")
        })
    }
}
