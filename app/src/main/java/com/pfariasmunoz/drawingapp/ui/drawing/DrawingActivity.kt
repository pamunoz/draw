package com.pfariasmunoz.drawingapp.ui.drawing

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.pfariasmunoz.drawingapp.R
import com.pfariasmunoz.drawingapp.di.Injector
import com.pfariasmunoz.drawingapp.ui.signin.SigninActivity
import kotlinx.android.synthetic.main.activity_drawing.*

class DrawingActivity : AppCompatActivity(), DrawingContract.View {

    val presenter: DrawingPresenter

    init {
        presenter = Injector.get().drawingPresenter()
    }

    override val currentUserId: String
        get() = intent.getStringExtra(SigninActivity.CURRENT_USER_ID)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_drawing)

        btn_save_drawing.setOnClickListener({
            simpleDrawingView1.clear()
            //Log.i("DrawingActivity", "${simpleDrawingView1.mBitmap}")
        })
    }
}
