package com.pfariasmunoz.drawingapp.ui.userslist

import android.database.DataSetObserver
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.ArrayAdapter
import com.pfariasmunoz.drawingapp.R
import com.pfariasmunoz.drawingapp.di.Injector
import kotlinx.android.synthetic.main.activity_user_list.*

class UserListActivity : AppCompatActivity(), UserListContract.View {

    val tag = UserListActivity::class.java.simpleName

    lateinit var adapter: ArrayAdapter<String>

    val presenter: UserListPresenter

    init {
        this.presenter = Injector.get().userListPresenter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_list)
        Log.i("On USER LIST 1", " ${presenter.userNamesList}")
        presenter.loadUsers()
        Log.i("On USER LIST 2", " ${presenter.userNamesList}")
        val testList = arrayListOf("uno", "dos", "tres")
        setupAdapter()

    }

    override fun onResume() {
        super.onResume()
        presenter.setupView(this)
    }

    override fun showList() {
        presenter.loadUsers()
    }

    override fun setupAdapter() {
        val names = presenter.userNamesList
        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, names)
        user_list.adapter = adapter
    }

}
