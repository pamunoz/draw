package com.pfariasmunoz.drawingapp.ui.drawing

import com.pfariasmunoz.drawingapp.data.source.local.UsersLocalDataSource
import com.pfariasmunoz.drawingapp.di.Injector
import com.pfariasmunoz.drawingapp.util.launchSilent
import javax.inject.Inject
import kotlin.coroutines.experimental.CoroutineContext

class DrawingPresenter @Inject constructor(): DrawingContract.Presenter {

    lateinit var view: DrawingContract.View
    val usersDataSource : UsersLocalDataSource
    val uiContext: CoroutineContext

    init {
        this.usersDataSource = Injector.get().localUsersDataSource()
        this.uiContext = Injector.get().coroutineUIContext()
    }

    override fun setupView(view: DrawingContract.View) {
        this.view = view
    }

    override fun findUser() = launchSilent(uiContext) {
        usersDataSource.getUserById(view.currentUserId)
    }

}