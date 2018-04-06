package com.pfariasmunoz.drawingapp.ui.drawing

import com.pfariasmunoz.drawingapp.data.Result
import com.pfariasmunoz.drawingapp.data.source.local.UsersLocalDataSource
import com.pfariasmunoz.drawingapp.data.source.model.User
import com.pfariasmunoz.drawingapp.di.Injector
import com.pfariasmunoz.drawingapp.util.launchSilent
import javax.inject.Inject
import kotlin.coroutines.experimental.CoroutineContext

class DrawingPresenter @Inject constructor(): DrawingContract.Presenter {

    lateinit var view: DrawingContract.View
    val usersDataSource : UsersLocalDataSource
    val uiContext: CoroutineContext
    private lateinit var currentUser: User

    init {
        this.usersDataSource = Injector.get().localUsersDataSource()
        this.uiContext = Injector.get().coroutineUIContext()
    }

    override fun setupView(view: DrawingContract.View) {
        this.view = view
    }

    override fun findUser() = launchSilent(uiContext) {
        val result = usersDataSource.getUserById(view.currentUserId)
        when(result) {
            is Result.Success -> {
                currentUser = result.data
            }
            
        }
    }

}