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

    /**
     * This function cannot e reached if the [currentUserId] is empty.
     * So a user is searched in the database that correspond with the data provided.
     * @param login the login for the user trying to sign in
     * @param password the password for the user trying to sign in
     */
    override fun checkUserAndSignIn(login: String, password: String) = launchSilent(uiContext) {
        val result = usersDataSource.getUserByPassword(password)
        when (result) {
            is Result.Success -> {
                if (result.data.login == login && result.data.password == password) {
                    currentUserId = result.data.id
                    view.apply {
                        setCurrentUser(currentUserId)
                        signin()
                    }
                } else {
                    view.showSigninError()
                }
            }
            else -> view.showSigninError()
        }
    }
}