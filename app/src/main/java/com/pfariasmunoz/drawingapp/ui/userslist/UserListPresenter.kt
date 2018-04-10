package com.pfariasmunoz.drawingapp.ui.userslist

import com.pfariasmunoz.drawingapp.data.Result
import com.pfariasmunoz.drawingapp.data.source.local.UsersLocalDataSource
import com.pfariasmunoz.drawingapp.di.Injector
import com.pfariasmunoz.drawingapp.util.AppExecutors
import com.pfariasmunoz.drawingapp.util.launchSilent
import javax.inject.Inject

/**
 * This [UserListContract.Presenter] is in charge of the logic of
 * singing up of the user.
 */
@Suppress("JoinDeclarationAndAssignment")
class UserListPresenter @Inject constructor(): UserListContract.Presenter {

    /** The [UserListContract.View] that is in charge of the users input */
    lateinit var view: UserListContract.View
    /** The names of the users on the database */
    val userNamesList = ArrayList<String>()
    /** The repository for the user's data */
    private val usersDataSource : UsersLocalDataSource
    /** the context in which the ui work will be done */
    private val appExecutors: AppExecutors

    init {
        this.usersDataSource = Injector.get().localUsersDataSource()
        this.appExecutors = Injector.get().appExecutors()
    }

    /**
     * This method should be called at the start of the Activity so
     * it can use the presenter's data
     * @param view the view that will use the presenter
     */
    override fun setupView(view: UserListContract.View) {
        this.view = view
    }

    /**
     * Load the list of users from the repository
     */
    override fun loadUsers() = launchSilent(appExecutors.uiContext) {
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