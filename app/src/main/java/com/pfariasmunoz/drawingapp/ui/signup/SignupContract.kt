package com.pfariasmunoz.drawingapp.ui.signup

import com.pfariasmunoz.drawingapp.data.source.model.User

interface SignupContract {
    interface View {
        fun getUserName(): String
        fun getFirstPassword(): String
        fun getSecondPassword(): String
    }

    interface Presenter {
        var currentUserId: String
        fun setUpView(view: View)
        fun saveUser()
        fun checkedPassword(): Boolean
    }
}