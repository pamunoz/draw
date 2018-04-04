package com.pfariasmunoz.drawingapp.ui.signin

interface SinginContract {

    interface View {
        fun findUser(userId: String): Boolean
    }

    interface Presenter {
        fun funSetupView(view: View)
    }
}