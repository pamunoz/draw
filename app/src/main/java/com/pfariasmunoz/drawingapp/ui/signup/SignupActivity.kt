package com.pfariasmunoz.drawingapp.ui.signup

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.pfariasmunoz.drawingapp.R
import com.pfariasmunoz.drawingapp.di.Injector
import com.pfariasmunoz.drawingapp.util.*
import kotlinx.android.synthetic.main.activity_signup.*


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
        val returnIntent = Intent()
        //returnIntent.putExtra(CURRENT_USER_ID, presenter.currentUser?.id)
        setResult(Activity.RESULT_OK, returnIntent)
        finish()
    }

    override fun showErronPassowdsDontMantch() {
        okDialog(resources.getString(R.string.error_sign_up))
    }
}
