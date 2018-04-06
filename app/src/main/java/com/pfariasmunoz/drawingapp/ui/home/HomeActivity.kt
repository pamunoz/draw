package com.pfariasmunoz.drawingapp.ui.home

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.pfariasmunoz.drawingapp.R
import com.pfariasmunoz.drawingapp.ui.drawing.DrawingActivity
import com.pfariasmunoz.drawingapp.ui.signin.SigninActivity
import com.pfariasmunoz.drawingapp.ui.userslist.UserListActivity
import com.pfariasmunoz.drawingapp.util.launchActivity
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        setSupportActionBar(home_toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
        }

        btn_draw.setOnClickListener({
            launchActivity<DrawingActivity>() {
                val currentUserId = intent.getStringExtra(SigninActivity.CURRENT_USER_ID)
                putExtra(SigninActivity.CURRENT_USER_ID, currentUserId)
            }
        })
        btn_users_list.setOnClickListener({
            launchActivity<UserListActivity>()
        })

    }
}
