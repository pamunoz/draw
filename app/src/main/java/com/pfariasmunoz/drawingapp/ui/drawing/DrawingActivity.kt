package com.pfariasmunoz.drawingapp.ui.drawing

import android.graphics.Bitmap
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
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
        setSupportActionBar(drawing_toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
        }
        presenter.setupView(this)
        presenter.loadUserDrawing()

        btn_save_drawing.setOnClickListener({
            saveUserDrawing()
        })
        btn_clear_drawing.setOnClickListener({
            simpleDrawingView1.clear()
        })
    }

    override fun getDrawing() = simpleDrawingView1.mBitmap

    override fun draw(bitmap: Bitmap) {
        simpleDrawingView1.mBitmap = bitmap
    }

    override fun saveUserDrawing() {
        simpleDrawingView1.setBitmap(getDrawing())
        presenter.saveUser()
    }

}
