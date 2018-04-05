package com.pfariasmunoz.drawingapp.ui.userslist

import com.pfariasmunoz.drawingapp.data.Result
import com.pfariasmunoz.drawingapp.data.source.local.UsersLocalDataSource
import com.pfariasmunoz.drawingapp.di.Injector
import com.pfariasmunoz.drawingapp.util.launchSilent
import javax.inject.Inject
import kotlin.coroutines.experimental.CoroutineContext

class UserListPresenter @Inject constructor(): UserListContract.Presenter {

    lateinit var view: UserListContract.View
    val userNamesList = ArrayList<String>()
    val usersDataSource : UsersLocalDataSource
    val uiContext: CoroutineContext

    init {
        this.usersDataSource = Injector.get().localUsersDataSource()
        this.uiContext = Injector.get().coroutineUIContext()
    }


    override fun setupView(view: UserListContract.View) {
        this.view = view
    }

    override fun loadUsers() = launchSilent(uiContext) {
        val result = usersDataSource.getUsers()
        when(result) {
            is Result.Success -> {
                for (user in result.data) {
                    userNamesList.add(user.login)
                }
                view.setupAdapter()
            }
        }
    }

}