package com.pfariasmunoz.drawingapp.ui.signin

import javax.inject.Inject

class SigninPresenter @Inject constructor(): SinginContract.Presenter {

    lateinit var view: SinginContract.View

    override fun funSetupView(view: SinginContract.View) {
        this.view = view
    }

}