package com.pfariasmunoz.drawingapp.ui.userslist

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.ArrayAdapter
import com.pfariasmunoz.drawingapp.R
import com.pfariasmunoz.drawingapp.di.Injector
import kotlinx.android.synthetic.main.activity_user_list.*

class UserListActivity : AppCompatActivity(), UserListContract.View {

    val tag = UserListActivity::class.java.simpleName

    val presenter: UserListPresenter

    init {
        this.presenter = Injector.get().userListPresenter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_list)
        showList()
        val names = presenter.userList.map { user -> user.login }
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, names)
        names.forEach {
            user -> Log.i(tag, "User:  $user")
        }
        user_list.adapter = adapter
    }

    override fun showList() {
        presenter.loadUsers()
    }

}
