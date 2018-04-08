package com.pfariasmunoz.drawingapp.ui.signup

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.pfariasmunoz.drawingapp.R
import com.pfariasmunoz.drawingapp.di.Injector
import com.pfariasmunoz.drawingapp.ui.signin.SigningActivity
import com.pfariasmunoz.drawingapp.util.CURRENT_USER_ID
import com.pfariasmunoz.drawingapp.util.preferences
import com.pfariasmunoz.drawingapp.util.put
import com.pfariasmunoz.drawingapp.util.toast
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
        presenter.setUpView(this)
        val userId = preferences.getString(CURRENT_USER_ID, "")
        toast("current id: $userId")
        if (userId.isNotEmpty()) {
            presenter.currentUserId = userId
        }
        setListeners()
    }

    override fun getUserName() = login_edittext.text.toString()

    override fun getFirstPassword() = first_password_edittext.text.toString()

    override fun getSecondPassword(): String {
        return confirm_password_edittext.text.toString()
    }

    private fun setListeners() {
        btn_signin.setOnClickListener({
            finish()
        })

        btn_register.setOnClickListener({
            if (presenter.checkedPassword()) {
                presenter.saveUser()
                if (presenter.currentUserId!!.isNotEmpty()) {
                    preferences.put(CURRENT_USER_ID, presenter.currentUserId)
                    finish()
                }
            } else {
                alert {
                    title = "Passwords don't match!:"
                    okButton {  }
                }
            }
        })
    }
}
