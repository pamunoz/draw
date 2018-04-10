package com.pfariasmunoz.drawingapp.ui.signup

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.pfariasmunoz.drawingapp.R
import com.pfariasmunoz.drawingapp.di.Injector
import com.pfariasmunoz.drawingapp.util.*
import kotlinx.android.synthetic.main.activity_signup.*

/**
 * This [Activity] is in charge of the process of singing up
 */
@Suppress("JoinDeclarationAndAssignment")
class SignupActivity : AppCompatActivity(), SignupContract.View {

    /** The [SignupContract.Presenter] in charge of the logic */
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

    /**
     * Set the listener for the sign up button and the sign up button.
     * The sign in button start SingingActivity.
     * the sign up button add the user to the database.
     */
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

    /**
     * Add the user id to the preferences.
     * And set the singing up process as successful
     */
    override fun registerUser() {
        preferences.put(CURRENT_USER_ID, presenter.currentUser?.id)
        val returnIntent = Intent()
        setResult(Activity.RESULT_OK, returnIntent)
        finish()
    }

    override fun showErrorPasswordsDontMantch() {
        okDialog(resources.getString(R.string.error_sign_up))
    }
}
