package com.pfariasmunoz.drawingapp.ui.signin

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.pfariasmunoz.drawingapp.R
import com.pfariasmunoz.drawingapp.di.Injector
import com.pfariasmunoz.drawingapp.ui.home.HomeActivity
import com.pfariasmunoz.drawingapp.ui.signup.SignupActivity
import com.pfariasmunoz.drawingapp.util.*
import kotlinx.android.synthetic.main.activity_signin.*

/**
 * This [Activity] is in charge of the sing in process
 */
@Suppress("JoinDeclarationAndAssignment")
class SigningActivity : AppCompatActivity(), SingingContract.View {

    /**
     * The presenter that is in charge of the logic of the process
     * of signing in.
     */
    val presenter: SigningPresenter

    init {
        this.presenter = Injector.get().signinPresenter()
    }

    companion object {
        const val REQUEST_CODE = 1
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signin)
        with(presenter) {
            setupView(this@SigningActivity)
            /* If a user already sign in, start the home activity */
            currentUserId = preferences.getString(CURRENT_USER_ID, "")
            if (currentUserId.isNotEmpty()) signin()
        }
        /* set the listener for the widgets of the activity */
        setListeners()
    }

    /**
     * Set the listener for the sign in button and the sign up button.
     * The sign in button checks if the user is in the database and
     * the sign up button start the sign up process in the sign up activity
     */
    private fun setListeners() {
        btn_sign_up.setOnClickListener({
            launchActivity<SignupActivity>(REQUEST_CODE)
        })
        btn_sign_in.setOnClickListener({
            val login = username_edittext.text.toString()
            val password = password_edittext.text.toString()
            presenter.checkUserAndSignIn(login, password)
        })
    }

    /**
     * Start a error dialog if the values inserted by the user
     * are incorrect.
     */
    override fun showSigninError() {
        okDialog(resources.getString(R.string.error_sign_in))
    }

    /**
     * Start the [HomeActivity]
     */
    override fun signin() {
        launchActivity<HomeActivity>()
    }

    /**
     * Display the values of the user that sign up.
     * @param login the user's login from the database.
     * @param password the password of the user that sing up.
     */
    override fun displayUserSignedIn(login: String, password: String) {
        username_edittext.setText(login)
        password_edittext.setText(password)
    }

    /**
     * Update the current user signed in.
     * @param id the id of the current user signed in.
     */
    override fun setCurrentUser(id: String) {
        preferences.put(CURRENT_USER_ID, id)
    }

    /**
     * Display a corresponding method corresponding if the user signing up was or not successful
     */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            okDialog(resources.getString(R.string.success_sign_up))
        } else {
            toast(resources.getString(R.string.failure_sign_up))
        }
    }
}
