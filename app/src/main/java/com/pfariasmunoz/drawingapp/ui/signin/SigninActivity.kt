package com.pfariasmunoz.drawingapp.ui.signin

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v4.view.GravityCompat
import android.support.v7.app.AppCompatActivity
import android.view.Gravity
import com.pfariasmunoz.drawingapp.R
import com.pfariasmunoz.drawingapp.di.Injector
import com.pfariasmunoz.drawingapp.ui.home.HomeActivity
import com.pfariasmunoz.drawingapp.ui.signup.SignupActivity
import com.pfariasmunoz.drawingapp.util.launchActivity
import kotlinx.android.synthetic.main.activity_signin.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.customView
import org.jetbrains.anko.okButton


class SigninActivity : AppCompatActivity(), SinginContract.View {

    val presenter: SigninPresenter

    init {
        this.presenter = Injector.get().signinPresenter()
    }

    companion object {
        val REQUEST_CODE = 1
        val CURRENT_USER_ID = "current_user_id"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signin)

        presenter.setupView(this)

        if (presenter.currentUserId.isNotEmpty()) launchActivity<HomeActivity> {
            putExtra(CURRENT_USER_ID, presenter.currentUserId)
        }
        setListeners()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                presenter.currentUserId = data?.getStringExtra(CURRENT_USER_ID) ?: ""
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                presenter.currentUserId = ""
            }
        }
    }

    private fun setListeners() {
        btn_sign_up.setOnClickListener({
            launchActivity<SignupActivity>(REQUEST_CODE)
        })
        btn_sign_in.setOnClickListener({
            val login = username_edittext.text.toString()
            val password = password_edittext.text.toString()
            presenter.checkUser(login, password)
        })

    }

    override fun showSigninError() {
        alert {
            title = "Incorrect user/password combination"
            okButton { Gravity.CENTER_HORIZONTAL

            }

        }.show()
    }

    override fun signin() {
        launchActivity<HomeActivity>{ putExtra(CURRENT_USER_ID, presenter.currentUserId)}
    }

}
