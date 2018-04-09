package com.pfariasmunoz.drawingapp.ui.home

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.widget.Button
import android.widget.TextView
import com.pfariasmunoz.drawingapp.R
import com.pfariasmunoz.drawingapp.ui.drawing.DrawingActivity
import com.pfariasmunoz.drawingapp.ui.signin.SigningActivity
import com.pfariasmunoz.drawingapp.ui.userslist.UserListActivity
import com.pfariasmunoz.drawingapp.util.*
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        setSupportActionBar(home_toolbar)
        supportActionBar?.apply {
            //setDisplayHomeAsUpEnabled(true)
            val title = findViewById<TextView>(R.id.title_toolbar)
            title?.text = resources.getString(R.string.home_value)
            val button = findViewById<Button>(R.id.btn_toolbar)
            button?.text = resources.getString(R.string.toolbar_button_home)
            button?.setOnClickListener({
                preferences.put(CURRENT_USER_ID, "")
                launchActivity<SigningActivity>()
            })
        }

        btn_draw.setOnClickListener({
            //launchActivity<DrawActivity>()
            launchActivity<DrawingActivity>()
        })
        btn_users_list.setOnClickListener({
            launchActivity<UserListActivity>()
        })
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when(item?.itemId) {
            android.R.id.home -> {
                preferences.put(CURRENT_USER_ID, "")
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
