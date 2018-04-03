package com.pfariasmunoz.drawingapp.ui.signup

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.pfariasmunoz.drawingapp.R
import com.pfariasmunoz.drawingapp.di.Injector
import com.pfariasmunoz.drawingapp.ui.signin.SigninActivity
import com.pfariasmunoz.drawingapp.util.positiveButton
import com.pfariasmunoz.drawingapp.util.showAlertDialog
import kotlinx.android.synthetic.main.activity_signin.*
import kotlinx.android.synthetic.main.activity_signup.*

class SignupActivity : AppCompatActivity(), SignupContract.View {

    val presenter: SignupPresenter

    init {
        this.presenter = Injector.get().signupPresenter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)
        presenter.setUpView(this)
        setListeners()
    }

    override fun getUserName() = login_edittext.text.toString()

    override fun getFirstPassword() = first_password_edittext.text.toString()

    override fun getSecondPassword(): String {
        return confirm_password_edittext.text.toString()
    }

    private fun setListeners() {
        btn_signin.setOnClickListener({
            val intent = Intent(this, SigninActivity::class.java)
            startActivity(intent)
        })

        btn_register.setOnClickListener({
            if (presenter.checkedPassword()) {
                presenter.saveUser()
            } else {
                showAlertDialog {
                    title = "nothing"
                    setMessage("Passwords don't match!")
                    positiveButton("OK") {

                    }
                }
            }
        })
    }
}
