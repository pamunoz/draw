package com.pfariasmunoz.drawingapp.ui.signup

import com.pfariasmunoz.drawingapp.data.source.model.User
/** The distribution of work between the view and the presenter */
interface SignupContract {
    interface View {
        fun registerUser()
        fun showErrorPasswordsDontMantch()
    }

    interface Presenter {
        var currentUser: User?
        fun setupView(view: View)
        fun saveUser(login: String, password: String, confirmPassword: String)
    }
}