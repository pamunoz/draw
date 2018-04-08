package com.pfariasmunoz.drawingapp.ui.signin

interface SingingContract {



    interface View {
        fun showSigninError()
        fun signin()
        fun displayUserSignedIn(login: String, password: String)
    }

    interface Presenter {
        var currentUserId: String
        fun setupView(view: View)
        fun findUser()
        fun checkUserAndSignIn(login: String, password: String)

    }
}