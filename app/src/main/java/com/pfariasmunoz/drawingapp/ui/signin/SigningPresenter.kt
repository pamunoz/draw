package com.pfariasmunoz.drawingapp.ui.signin

import com.pfariasmunoz.drawingapp.data.Result
import com.pfariasmunoz.drawingapp.data.source.local.UsersLocalDataSource
import com.pfariasmunoz.drawingapp.di.Injector
import com.pfariasmunoz.drawingapp.util.AppExecutors
import com.pfariasmunoz.drawingapp.util.launchSilent
import javax.inject.Inject

/**
 * This [SingingContract.Presenter] is in charge of the logic of
 * singing in of the user.
 */
@Suppress("JoinDeclarationAndAssignment")
class SigningPresenter @Inject constructor() : SingingContract.Presenter {

    /** The [SingingContract.View] that is in charge of the users input */
    lateinit var view: SingingContract.View
    /** The repository for the user's data */
    private val usersDataSource: UsersLocalDataSource
    /** the context in which the ui work will be done */
    private val appExecutors: AppExecutors

    init {
        this.usersDataSource = Injector.get().localUsersDataSource()
        this.appExecutors = Injector.get().appExecutors()
    }

    override var currentUserId: String = ""

    /**
     * This method should be called at the start of the Activity so
     * it can use the presenter's data
     * @param view the view that will use the presenter
     */
    override fun setupView(view: SingingContract.View) {
        this.view = view
    }

    /**
     * This function cannot e reached if the [currentUserId] is empty.
     * So a user is searched in the database that correspond with the data provided.
     * @param login the login for the user trying to sign in
     * @param password the password for the user trying to sign in
     */
    override fun checkUserAndSignIn(login: String, password: String) = launchSilent(appExecutors.uiContext) {
        val result = usersDataSource.getUserByPassword(password)
        when (result) {
            is Result.Success -> {
                if (result.data.login.equals(login) && result.data.password.equals(password)) {
                    view.apply {
                        currentUserId = result.data.id
                        view.setCurrentUser(currentUserId)
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