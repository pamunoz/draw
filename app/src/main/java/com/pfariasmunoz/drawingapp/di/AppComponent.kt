package com.pfariasmunoz.drawingapp.di

import android.content.Context
import com.pfariasmunoz.drawingapp.data.source.local.UsersLocalDataSource
import com.pfariasmunoz.drawingapp.di.modules.ContextModule
import com.pfariasmunoz.drawingapp.di.modules.DatabaseModule
import com.pfariasmunoz.drawingapp.ui.signin.SigninPresenter
import com.pfariasmunoz.drawingapp.ui.signup.SignupPresenter
import com.pfariasmunoz.drawingapp.ui.userslist.UserListPresenter
import kotlinx.coroutines.experimental.android.UI
import dagger.Component
import kotlin.coroutines.experimental.CoroutineContext

@Component(modules = [ContextModule::class, DatabaseModule::class])
interface AppComponent {
    fun appContext(): Context

    fun signupPresenter(): SignupPresenter

    fun signinPresenter(): SigninPresenter

    fun userListPresenter(): UserListPresenter

    fun localUsersDataSource(): UsersLocalDataSource

    fun coroutineUIContext(): CoroutineContext


}