package com.pfariasmunoz.drawingapp.ui.signin

interface SinginContract {
    interface View {
        fun getUserName(): String
        fun getUserPassword(): String
        fun signIn()
    }

    interface Presenter {
        fun funSetupView(view: View)
    }
}