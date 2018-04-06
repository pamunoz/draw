package com.pfariasmunoz.drawingapp.ui.drawing

import com.pfariasmunoz.drawingapp.data.Result
import com.pfariasmunoz.drawingapp.data.source.local.UsersLocalDataSource
import com.pfariasmunoz.drawingapp.data.source.model.User
import com.pfariasmunoz.drawingapp.di.Injector
import com.pfariasmunoz.drawingapp.util.launchSilent
import com.pfariasmunoz.drawingapp.util.toBitmap
import com.pfariasmunoz.drawingapp.util.toByteArray
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.async
import javax.inject.Inject
import kotlin.coroutines.experimental.CoroutineContext

class DrawingPresenter @Inject constructor(): DrawingContract.Presenter {

    lateinit var view: DrawingContract.View
    private val bgContext: CoroutineContext = CommonPool
    val usersDataSource : UsersLocalDataSource
    val uiContext: CoroutineContext

    init {
        this.usersDataSource = Injector.get().localUsersDataSource()
        this.uiContext = Injector.get().coroutineUIContext()
    }

    override fun setupView(view: DrawingContract.View) {
        this.view = view
    }

    override fun loadUserDrawing() = launchSilent(uiContext) {
        val result = usersDataSource.getUserById(view.currentUserId)
        when(result) {
            is Result.Success -> {
                val currentUser = result.data
                view.draw(currentUser.drawing!!.toBitmap)
            }
        }
    }

    override fun saveUser() = launchSilent(uiContext) {
        val task =  async(bgContext) { view.getDrawing() }
        val bitmapResult = task.await()
        val bitmap = bitmapResult
        val result = usersDataSource.getUserById(view.currentUserId)
        when(result) {
            is Result.Success -> {
                val user = result.data
                user.drawing = bitmap.toByteArray
                usersDataSource.updateUser(user)
            }
        }
    }




}