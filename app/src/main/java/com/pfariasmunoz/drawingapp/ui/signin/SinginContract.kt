package com.pfariasmunoz.drawingapp.ui.signin

import com.pfariasmunoz.drawingapp.data.source.model.User

interface SinginContract {

    interface View {
        var currentUser: User
    }

    interface Presenter {
        fun funSetupView(view: View)
        fun findUser(userId: String): User
    }
}