package com.pfariasmunoz.drawingapp.ui.signup

import com.pfariasmunoz.drawingapp.data.source.UsersDataSource
import com.pfariasmunoz.drawingapp.data.source.model.User
import com.pfariasmunoz.drawingapp.util.launchSilent
import kotlinx.coroutines.experimental.android.UI
import java.util.*
import kotlin.collections.ArrayList
import kotlin.coroutines.experimental.CoroutineContext


class SignupPresenter(
        private val view: SignupContract.View,
        private val usersDataSource: UsersDataSource,
        private val uiContext: CoroutineContext = UI) : SignupContract.Presenter {


    override fun saveUser() = launchSilent(uiContext) {
        val login = view.getUserName()
        val password = if (checkedPassword()) view.getFirstPassword() else ""
        val byteArray = ByteArray(10)
        usersDataSource.saveUser(User(login = login, password = password, drawing = byteArray))

    }

    override fun checkedPassword(): Boolean {
        return  (view.getFirstPassword().equals(view.getSecondPassword()))
    }

}