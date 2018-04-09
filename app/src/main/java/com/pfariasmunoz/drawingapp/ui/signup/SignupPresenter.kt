package com.pfariasmunoz.drawingapp.ui.signup

import com.pfariasmunoz.drawingapp.data.Result
import com.pfariasmunoz.drawingapp.data.source.local.UsersLocalDataSource
import com.pfariasmunoz.drawingapp.data.source.model.User
import com.pfariasmunoz.drawingapp.di.Injector
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

    override var currentUser: User? = null

    override fun setupView(view: SignupContract.View) {
        this.view = view
    }

    override fun saveUser(login: String, password: String, confirmPassword: String) = launchSilent(uiContext) {
        if (password == confirmPassword) {
            currentUser = User(login = login, password = password)
            val result = usersDataSource.saveUser(currentUser!!)
            when(result) {
                is Result.Success -> view.registerUser()
                else -> view.showErronPassowdsDontMantch()
            }
        } else {
            view.showErronPassowdsDontMantch()
        }
    }


}