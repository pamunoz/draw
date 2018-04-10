package com.pfariasmunoz.drawingapp.ui.drawing

import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.widget.TextView
import com.pfariasmunoz.drawingapp.R
import com.pfariasmunoz.drawingapp.di.Injector
import com.pfariasmunoz.drawingapp.ui.home.HomeActivity
import com.pfariasmunoz.drawingapp.util.CURRENT_USER_ID
import com.pfariasmunoz.drawingapp.util.launchActivity
import com.pfariasmunoz.drawingapp.util.preferences
import kotlinx.android.synthetic.main.activity_drawing.*

@Suppress("JoinDeclarationAndAssignment")
class DrawingActivity : AppCompatActivity(), DrawingContract.View {

    val presenter: DrawingPresenter

    var source: Uri? = null

    init {
        presenter = Injector.get().drawingPresenter()
    }

    override val currentUserId: String
        get() = preferences.getString(CURRENT_USER_ID, "")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_drawing)
        setSupportActionBar(drawing_toolbar)
        supportActionBar?.apply {
            //setDisplayHomeAsUpEnabled(true)
            val title = findViewById<TextView>(R.id.title_toolbar)
            title?.text = resources.getString(R.string.draw_value)
            val button = findViewById<Button>(R.id.btn_toolbar)
            button?.text = resources.getString(R.string.home_value)
            button?.setOnClickListener({
                launchActivity<HomeActivity>()
            })
        }
        with(presenter) {
            setupView(this@DrawingActivity)
        }
        setupListeners()
    }

    private fun setupListeners() {

        btn_load_drawing.setOnClickListener({
            simpleDrawingView1.loadBitmap(preferences.getString(CURRENT_USER_ID, ""))
        })

        btn_save_drawing.setOnClickListener({
            simpleDrawingView1.isDrawingCacheEnabled = true
            simpleDrawingView1.saveBitmap(preferences.getString(CURRENT_USER_ID, ""))
        })

        btn_clear_drawing.setOnClickListener({
            simpleDrawingView1.clear()
        })
    }
}
