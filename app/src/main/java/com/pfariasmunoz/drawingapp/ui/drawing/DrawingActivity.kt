package com.pfariasmunoz.drawingapp.ui.drawing

import android.app.ActionBar
import android.graphics.Bitmap
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.pfariasmunoz.drawingapp.R
import com.pfariasmunoz.drawingapp.di.Injector
import com.pfariasmunoz.drawingapp.util.CURRENT_USER_ID
import com.pfariasmunoz.drawingapp.util.preferences
import kotlinx.android.synthetic.main.activity_drawing.*

class DrawingActivity : AppCompatActivity(), DrawingContract.View {


    val presenter: DrawingPresenter

    init {
        presenter = Injector.get().drawingPresenter()
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
