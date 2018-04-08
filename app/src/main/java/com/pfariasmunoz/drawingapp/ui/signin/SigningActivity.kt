package com.pfariasmunoz.drawingapp.ui.signin

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Gravity
import com.pfariasmunoz.drawingapp.R
import com.pfariasmunoz.drawingapp.data.source.local.UsersAppDatabase
import com.pfariasmunoz.drawingapp.di.Injector
import com.pfariasmunoz.drawingapp.ui.home.HomeActivity
import com.pfariasmunoz.drawingapp.ui.signup.SignupActivity
import com.pfariasmunoz.drawingapp.util.CURRENT_USER_ID
import com.pfariasmunoz.drawingapp.util.launchActivity
import com.pfariasmunoz.drawingapp.util.preferences
import com.pfariasmunoz.drawingapp.util.toast
import kotlinx.android.synthetic.main.activity_signin.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.okButton


class SigningActivity : AppCompatActivity(), SingingContract.View {

    val presenter: SigningPresenter

    init {
        this.presenter = Injector.get().signinPresenter()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signin)
        presenter.setupView(this)
        if (presenter.currentUserId.isNotEmpty()) signin()
        setListeners()
    }

    private fun setListeners() {
        btn_sign_up.setOnClickListener({
            launchActivity<SignupActivity>()
        })
        btn_sign_in.setOnClickListener({
            val login = username_edittext.text.toString()
            val password = password_edittext.text.toString()
            presenter.checkUserAndSignIn(login, password)
        })
    }

    override fun showSigninError() {
        alert {
            title = "Incorrect user/password combination"
            okButton { Gravity.CENTER_HORIZONTAL }
        }.show()
    }

    override fun signin() {
        launchActivity<HomeActivity>()
    }

    override fun displayUserSignedIn(login: String, password: String) {
        username_edittext.setText(login)
        password_edittext.setText(password)
    }
}
