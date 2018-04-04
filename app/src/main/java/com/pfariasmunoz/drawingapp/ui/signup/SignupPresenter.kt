package com.pfariasmunoz.drawingapp.ui.signup

import android.content.Intent
import com.pfariasmunoz.drawingapp.data.source.local.UsersLocalDataSource
import com.pfariasmunoz.drawingapp.data.source.model.User
import com.pfariasmunoz.drawingapp.di.Injector
import com.pfariasmunoz.drawingapp.ui.signin.SigninActivity
import com.pfariasmunoz.drawingapp.util.launchSilent
import javax.inject.Inject
import kotlin.coroutines.experimental.CoroutineContext


class SignupPresenter @Inject constructor() : SignupContract.Presenter {

    lateinit var view: SignupContract.View
    val usersDataSource : UsersLocalDataSource
    val uiContext: CoroutineContext


    init {
        this.usersDataSource = Injector.get().localUsersDataSource()
        this.uiContext = Injector.get().coroutineUIContext()
    }

    override var currentUserId: String?
        get() = ""
        set(value) {}

    override fun setUpView(view: SignupContract.View) {
        this.view = view
    }

    override fun saveUser() = launchSilent(uiContext) {
        val login = view.getUserName()
        val password = view.getFirstPassword()
        val byteArray = ByteArray(10)
        val currentUser = User(login = login, password = password, drawing = byteArray)
        currentUserId = currentUser.id
        if (login.isNotEmpty() && password.isNotEmpty()) {
            usersDataSource.saveUser(currentUser)
        }
    }

    override fun checkedPassword(): Boolean {
        return  (view.getFirstPassword().equals(view.getSecondPassword()))
    }

}