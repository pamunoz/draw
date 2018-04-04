package com.pfariasmunoz.drawingapp.ui.signin

import com.pfariasmunoz.drawingapp.data.source.local.UsersLocalDataSource
import com.pfariasmunoz.drawingapp.data.source.model.User
import com.pfariasmunoz.drawingapp.di.Injector
import javax.inject.Inject
import kotlin.coroutines.experimental.CoroutineContext

class SigninPresenter @Inject constructor(): SinginContract.Presenter {

    lateinit var view: SinginContract.View
    val usersDataSource : UsersLocalDataSource
    val uiContext: CoroutineContext

    init {
        this.usersDataSource = Injector.get().localUsersDataSource()
        this.uiContext = Injector.get().coroutineUIContext()
    }

    override fun funSetupView(view: SinginContract.View) {
        this.view = view
    }

    override fun findUser(userId: String): User {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}