package com.pfariasmunoz.drawingapp.ui.signin

import com.pfariasmunoz.drawingapp.data.source.model.User

interface SinginContract {



    interface View {
        fun showSigninError()
        fun signin()
    }

    interface Presenter {
        var currentUserId: String
        fun setupView(view: View)
        fun findUser(userId: String)
        fun checkUser(login: String, password: String)
    }
}