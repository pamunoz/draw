package com.pfariasmunoz.drawingapp.ui.signup

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.pfariasmunoz.drawingapp.R
import com.pfariasmunoz.drawingapp.di.Injector
import com.pfariasmunoz.drawingapp.util.CURRENT_USER_ID
import com.pfariasmunoz.drawingapp.util.preferences
import com.pfariasmunoz.drawingapp.util.put
import kotlinx.android.synthetic.main.activity_signup.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.okButton


class SignupActivity : AppCompatActivity(), SignupContract.View {

    val presenter: SignupPresenter

    init {
        this.presenter = Injector.get().signupPresenter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)
        with(presenter) {
            setupView(this@SignupActivity)
        }
        setListeners()
    }

    private fun setListeners() {
        btn_signin.setOnClickListener({
            finish()
        })

        btn_register.setOnClickListener({
            val login = login_edittext.text.toString()
            val password = first_password_edittext.text.toString()
            val confirmPassword = confirm_password_edittext.text.toString()
            presenter.saveUser(login, password, confirmPassword)
        })
    }

    override fun registerUser() {
        preferences.put(CURRENT_USER_ID, presenter.currentUser?.id)
        finish()
    }

    override fun showErronPassowdsDontMantch() {
        alert {
            title = "Passwords don't match!:"
            okButton {  }
        }
    }
}
