package com.pfariasmunoz.drawingapp.ui.userslist

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.pfariasmunoz.drawingapp.R
import com.pfariasmunoz.drawingapp.di.Injector

class UserListActivity : AppCompatActivity(), UserListContract.View {

    val presenter: UserListPresenter

    init {
        this.presenter = Injector.get().userListPresenter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_list)
    }

    override fun showList() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
