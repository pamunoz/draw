package com.pfariasmunoz.drawingapp.ui.signin

/** The distribution of work between the view and the presenter */
interface SingingContract {
    interface View {
        fun setCurrentUser(id: String)
        fun showSigninError()
        fun signin()
        fun displayUserSignedIn(login: String, password: String)
    }

    interface Presenter {
        var currentUserId: String
        fun setupView(view: View)
        fun checkUserAndSignIn(login: String, password: String)
    }
}