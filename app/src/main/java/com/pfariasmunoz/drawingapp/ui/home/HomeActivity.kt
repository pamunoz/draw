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

/**
 * Activity in charge of the navigation between the singing process, drawing and user list processes
 */
class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        setSupportActionBar(home_toolbar)
        supportActionBar?.apply {
            val title = findViewById<TextView>(R.id.title_toolbar)
            title?.text = resources.getString(R.string.home_value)
            val button = findViewById<Button>(R.id.btn_toolbar)
            button?.text = resources.getString(R.string.toolbar_button_home)
            button?.setOnClickListener({
                preferences.put(CURRENT_USER_ID, "")
                launchActivity<SigningActivity>()
            })
        }
        setListeners()
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

    /**
     * Set the listener for the draw button and the user list button.
     * The draw button start DrawingActivity.
     * the user list button start the UserListActivity.
     */
    fun setListeners() {
        btn_draw.setOnClickListener({
            //launchActivity<DrawActivity>()
            launchActivity<DrawingActivity>()
        })
        btn_users_list.setOnClickListener({
            launchActivity<UserListActivity>()
        })
    }
}
