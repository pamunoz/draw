package com.pfariasmunoz.drawingapp.di

import android.content.Context
import com.pfariasmunoz.drawingapp.data.source.local.UsersLocalDataSource
import com.pfariasmunoz.drawingapp.di.modules.ContextModule
import com.pfariasmunoz.drawingapp.di.modules.DatabaseModule
import com.pfariasmunoz.drawingapp.ui.drawing.DrawingPresenter
import com.pfariasmunoz.drawingapp.ui.signin.SigningPresenter
import com.pfariasmunoz.drawingapp.ui.signup.SignupPresenter
import com.pfariasmunoz.drawingapp.ui.userslist.UserListPresenter
import dagger.Component
import kotlin.coroutines.experimental.CoroutineContext

@Component(modules = [ContextModule::class, DatabaseModule::class])
interface AppComponent {
    fun appContext(): Context

    fun signupPresenter(): SignupPresenter

    fun signinPresenter(): SigningPresenter

    fun userListPresenter(): UserListPresenter

    fun drawingPresenter(): DrawingPresenter

    fun localUsersDataSource(): UsersLocalDataSource

    fun coroutineUIContext(): CoroutineContext


}