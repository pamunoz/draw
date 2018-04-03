package com.pfariasmunoz.drawingapp.ui.signup

import com.pfariasmunoz.drawingapp.data.source.model.User

interface SignupContract {
    interface View {
        fun getUserName(): String
        fun getFirstPassword(): String
        fun getSecondPassword(): String
        fun signup()
    }

    interface Presenter {
        fun saveUser()
        fun checkedPassword(): String
    }
}