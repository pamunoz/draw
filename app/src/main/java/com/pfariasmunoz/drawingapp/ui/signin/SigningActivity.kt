package com.pfariasmunoz.drawingapp.ui.signin

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Gravity
import com.pfariasmunoz.drawingapp.R
import com.pfariasmunoz.drawingapp.data.source.local.UsersAppDatabase
import com.pfariasmunoz.drawingapp.di.Injector
import com.pfariasmunoz.drawingapp.ui.home.HomeActivity
import com.pfariasmunoz.drawingapp.ui.signup.SignupActivity
import com.pfariasmunoz.drawingapp.util.*
import kotlinx.android.synthetic.main.activity_signin.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.okButton


class SigningActivity : AppCompatActivity(), SingingContract.View {

    val presenter: SigningPresenter

    init {
        this.presenter = Injector.get().signinPresenter()
    }

    companion object {
        val REQUEST_CODE = 1
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signin)
        with(presenter) {
            setupView(this@SigningActivity)
            currentUserId = preferences.getString(CURRENT_USER_ID, "")
            if (currentUserId.isNotEmpty()) signin()
        }
        setListeners()
    }

    private fun setListeners() {
        btn_sign_up.setOnClickListener({
            launchActivity<SignupActivity>(REQUEST_CODE)
        })
        btn_sign_in.setOnClickListener({
            toast("User id = ${presenter.currentUserId}")
            if (preferences.getString(CURRENT_USER_ID, "").isEmpty()) {
                val login = username_edittext.text.toString()
                val password = password_edittext.text.toString()
                presenter.checkUserAndSignIn(login, password)
            }
        })
    }

    override fun showSigninError() {
        okDialog(resources.getString(R.string.error_sign_in))
    }

    override fun signin() {
        launchActivity<HomeActivity>()
    }

    override fun displayUserSignedIn(login: String, password: String) {
        username_edittext.setText(login)
        password_edittext.setText(password)
    }

    override fun setCurrentUser(id: String) {
        preferences.put(CURRENT_USER_ID, id)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when(requestCode) {
            Activity.RESULT_OK -> showSigninSuccess()
            else -> super.onActivityResult(requestCode, resultCode, data)
        }
    }

    override fun showSigninSuccess() {
        okDialog(resources.getString(R.string.success_sign_in))
    }
}
