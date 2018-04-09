package com.pfariasmunoz.drawingapp.ui.signup

import com.pfariasmunoz.drawingapp.data.source.model.User

interface SignupContract {
    interface View {
        fun registerUser()
        fun showErronPassowdsDontMantch()
    }

    interface Presenter {
        var currentUser: User?
        fun setupView(view: View)
        fun saveUser(login: String, password: String, confirmPassword: String)
        //fun checkedPasswords(firstPassWord: String, secondPassword: String): Boolean

    }
}