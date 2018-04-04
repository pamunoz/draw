package com.pfariasmunoz.drawingapp.ui.signin

interface SinginContract {



    interface View {
        fun showSigninError()
        fun signin()
        fun displayUserSignedIn(login: String, password: String)
    }

    interface Presenter {
        var currentUserId: String
        fun setupView(view: View)
        fun findUser(userId: String)
        fun checkUser(login: String, password: String)
    }
}