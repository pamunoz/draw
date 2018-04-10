package com.pfariasmunoz.drawingapp.ui.signup

import com.pfariasmunoz.drawingapp.data.Result
import com.pfariasmunoz.drawingapp.data.source.local.UsersLocalDataSource
import com.pfariasmunoz.drawingapp.data.source.model.User
import com.pfariasmunoz.drawingapp.di.Injector
import com.pfariasmunoz.drawingapp.util.launchSilent
import javax.inject.Inject
import kotlin.coroutines.experimental.CoroutineContext

/**
 * This [SignupContract.Presenter] is in charge of the logic of
 * singing up of the user.
 */
@Suppress("JoinDeclarationAndAssignment")
class SignupPresenter @Inject constructor() : SignupContract.Presenter {

    /** The [SignupContract.View] that is in charge of the users input */
    lateinit var view: SignupContract.View
    /** The repository for the user's data */
    private val usersDataSource : UsersLocalDataSource
    /** the context in which the ui work will be done */
    private val uiContext: CoroutineContext

    init {
        this.usersDataSource = Injector.get().localUsersDataSource()
        this.uiContext = Injector.get().coroutineUIContext()
    }

    /** The user that will be create by the signing up process */
    override var currentUser: User? = null

    /**
     * This method should be called at the start of the Activity so
     * it can use the presenter's data
     * @param view the view that will use the presenter
     */
    override fun setupView(view: SignupContract.View) {
        this.view = view
    }

    /**
     * Insert the [User] in the database, if both the password match.
     * @param login the login that the user put in on the sing up form
     * @param password the first password the user put in to sign up
     * @param confirmPassword the second password for confirmation that the user put in.
     */
    override fun saveUser(login: String, password: String, confirmPassword: String) =
            launchSilent(uiContext) {
        if (password == confirmPassword) {
            currentUser = User(login = login, password = password)
            val result = usersDataSource.saveUser(currentUser!!)
            when(result) {
                is Result.Success -> view.registerUser()
                else -> view.showErrorPasswordsDontMantch()
            }
        } else {
            view.showErrorPasswordsDontMantch()
        }
    }


}