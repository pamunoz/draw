package com.pfariasmunoz.drawingapp.ui.userslist

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.TextView
import com.pfariasmunoz.drawingapp.R
import com.pfariasmunoz.drawingapp.di.Injector
import com.pfariasmunoz.drawingapp.ui.home.HomeActivity
import com.pfariasmunoz.drawingapp.util.launchActivity
import kotlinx.android.synthetic.main.activity_user_list.*

class UserListActivity : AppCompatActivity(), UserListContract.View {

    val presenter: UserListPresenter

    init {
        this.presenter = Injector.get().userListPresenter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_list)
        setSupportActionBar(user_list_toolbar)
        supportActionBar?.apply {
            //setDisplayHomeAsUpEnabled(true)
            val title = findViewById<TextView>(R.id.title_toolbar)
            title?.text = "Registered Users"
            val button = findViewById<Button>(R.id.btn_toolbar)
            button?.text = "Home"
            button?.setOnClickListener({
                launchActivity<HomeActivity>()
            })
        }
        setupAdapter()
    }

    override fun onResume() {
        super.onResume()
        presenter.apply {
            setupView(this@UserListActivity)
            loadUsers()
        }
    }

    override fun setupAdapter() {
        user_list.adapter = ArrayAdapter(this, R.layout.item_user, presenter.userNamesList)
    }

}
