package com.pfariasmunoz.drawingapp.ui.home

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.pfariasmunoz.drawingapp.R
import com.pfariasmunoz.drawingapp.ui.drawing.DrawingActivity
import com.pfariasmunoz.drawingapp.ui.signin.SigningActivity
import com.pfariasmunoz.drawingapp.ui.userslist.UserListActivity
import com.pfariasmunoz.drawingapp.util.exist
import com.pfariasmunoz.drawingapp.util.launchActivity
import com.pfariasmunoz.drawingapp.util.toast
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        setSupportActionBar(home_toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
        }
        val currentUserId = intent.getStringExtra(SigningActivity.CURRENT_USER_ID)

        btn_draw.setOnClickListener({

            if (currentUserId.exist()) {
                toast("Current user id: $currentUserId")
                launchActivity<DrawingActivity> {
                    putExtra(SigningActivity.CURRENT_USER_ID, currentUserId)
                }
            }

        })
        btn_users_list.setOnClickListener({
            toast("Current user id: $currentUserId")
            launchActivity<UserListActivity>()
        })

    }
}
