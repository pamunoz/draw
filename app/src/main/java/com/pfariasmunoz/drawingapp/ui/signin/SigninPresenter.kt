package com.pfariasmunoz.drawingapp.ui.signin

import com.pfariasmunoz.drawingapp.data.Result
import com.pfariasmunoz.drawingapp.data.source.local.UsersLocalDataSource
import com.pfariasmunoz.drawingapp.di.Injector
import com.pfariasmunoz.drawingapp.util.launchSilent
import javax.inject.Inject
import kotlin.coroutines.experimental.CoroutineContext

class SigninPresenter @Inject constructor() : SinginContract.Presenter {

    lateinit var view: SinginContract.View
    val usersDataSource: UsersLocalDataSource
    val uiContext: CoroutineContext

    init {
        this.usersDataSource = Injector.get().localUsersDataSource()
        this.uiContext = Injector.get().coroutineUIContext()
    }

    override var currentUserId: String
        get() = ""
        set(value) {}

    override fun funSetupView(view: SinginContract.View) {
        this.view = view
    }

    override fun findUser(userId: String) = launchSilent(uiContext) {
        val result = usersDataSource.getUserById(userId)
        currentUserId = (result as? Result.Success)?.data?.id ?: ""
    }

    override fun checkUser(login: String, password: String) = launchSilent(uiContext) {
        var memoryLogin = ""
        var memoryPassword = ""
        val result = usersDataSource.getUserByPassword(password)
        when (result) {
            is Result.Success -> {
                memoryLogin = result.data.login
                memoryPassword = result.data.password
            }
            else -> view.showSigninError()
        }
        if (login.isNotEmpty() && password.isNotEmpty()) {
            if (memoryLogin.equals(login) && memoryPassword.equals(password)) {
                view.signin()
            } else {
                view.showSigninError()
            }
        }

    }
}