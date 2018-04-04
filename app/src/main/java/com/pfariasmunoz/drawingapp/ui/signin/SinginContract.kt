package com.pfariasmunoz.drawingapp.ui.signin

import com.pfariasmunoz.drawingapp.data.source.model.User

interface SinginContract {



    interface View {
        var userId: String?
    }

    interface Presenter {
        var currentUser: User
        fun funSetupView(view: View)
        fun findUser(userId: String): User
    }
}