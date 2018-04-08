package com.pfariasmunoz.drawingapp.ui.signup

import com.pfariasmunoz.drawingapp.data.source.local.UsersLocalDataSource
import com.pfariasmunoz.drawingapp.data.source.model.User
import com.pfariasmunoz.drawingapp.di.Injector
import com.pfariasmunoz.drawingapp.util.exist
import com.pfariasmunoz.drawingapp.util.launchSilent
import timber.log.Timber
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

    override var currentUserId: String? = ""

    override fun setUpView(view: SignupContract.View) {
        this.view = view
    }

    override fun saveUser() = launchSilent(uiContext) {
        val login = view.getUserName()
        val password = view.getFirstPassword()
        val byteArray = ByteArray(10)
        val currentUser = User(login = login, password = password, drawing = byteArray)
        currentUserId = currentUser.id
        if (login.exist()) {
            usersDataSource.saveUser(currentUser)
        }
    }

    override fun checkedPassword(): Boolean {
        return  (view.getFirstPassword().equals(view.getSecondPassword()))
    }

}