package com.pfariasmunoz.drawingapp.ui.signin

import com.pfariasmunoz.drawingapp.data.Result
import com.pfariasmunoz.drawingapp.data.source.local.UsersLocalDataSource
import com.pfariasmunoz.drawingapp.di.Injector
import com.pfariasmunoz.drawingapp.util.exist
import com.pfariasmunoz.drawingapp.util.launchSilent
import javax.inject.Inject
import kotlin.coroutines.experimental.CoroutineContext

class SigningPresenter @Inject constructor() : SingingContract.Presenter {

    lateinit var view: SingingContract.View
    val usersDataSource: UsersLocalDataSource
    val uiContext: CoroutineContext

    init {
        this.usersDataSource = Injector.get().localUsersDataSource()
        this.uiContext = Injector.get().coroutineUIContext()
    }

    override var currentUserId: String = ""

    override fun setupView(view: SingingContract.View) {
        this.view = view
    }

    override fun findUser() = launchSilent(uiContext) {
        val result = usersDataSource.getUserById(currentUserId)
        when(result) {
            is Result.Success -> {
                currentUserId = result.data.id
                view.displayUserSignedIn(result.data.login, result.data.password)
            }
        }
    }

    override fun checkUserAndSignIn(login: String, password: String) = launchSilent(uiContext) {
        var memoryLogin = ""
        var memoryPassword = ""
        val result = usersDataSource.getUserByPassword(password)
        when (result) {
            is Result.Success -> {
                currentUserId = result.data.id
                memoryLogin = result.data.login
                memoryPassword = result.data.password
            }
            else -> view.showSigninError()
        }
        if (currentUserId.isNotEmpty()) {
            if (memoryLogin.equals(login) && memoryPassword.equals(password)) {
                view.signin()
            } else {
                view.showSigninError()
            }
        }

    }
}