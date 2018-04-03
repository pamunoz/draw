package com.pfariasmunoz.drawingapp.ui.signup

import com.pfariasmunoz.drawingapp.data.source.UsersDataSource
import com.pfariasmunoz.drawingapp.data.source.model.User
import java.util.*
import kotlin.collections.ArrayList


class SignupPresenter(
        val view: SignupContract.View,
        val usersDataSource: UsersDataSource) : SignupContract.Presenter {


    override fun saveUser() {
        val login = view.getUserName()
        val password = checkedPassword()
        val byteArray = ByteArray(10)
        
    }

    override fun checkedPassword(): String {
        return if (view.getFirstPassword().equals(view.getSecondPassword())) {
            view.getFirstPassword()
        } else {
            ""
        }
    }

}