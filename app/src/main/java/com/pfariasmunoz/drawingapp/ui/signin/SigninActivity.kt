package com.pfariasmunoz.drawingapp.ui.signin

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.pfariasmunoz.drawingapp.R
import com.pfariasmunoz.drawingapp.di.Injector
import com.pfariasmunoz.drawingapp.ui.home.HomeActivity
import com.pfariasmunoz.drawingapp.ui.signup.SignupActivity
import com.pfariasmunoz.drawingapp.util.toast
import kotlinx.android.synthetic.main.activity_signin.*
import studios.codelight.smartloginlibrary.SmartLogin
import studios.codelight.smartloginlibrary.SmartLoginCallbacks
import studios.codelight.smartloginlibrary.SmartLoginConfig
import studios.codelight.smartloginlibrary.UserSessionManager
import studios.codelight.smartloginlibrary.users.SmartUser
import studios.codelight.smartloginlibrary.util.SmartLoginException


class SigninActivity : AppCompatActivity(), SmartLoginCallbacks, SinginContract.View {

    val presenter: SigninPresenter

    init {
        this.presenter = Injector.get().signinPresenter()
    }

    override fun getUserName(): String {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getUserPassword(): String {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun signIn() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    lateinit var user: SmartUser
    lateinit var config: SmartLoginConfig
    lateinit var login: SmartLogin

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signin)
        setListeners()

        config = SmartLoginConfig(this, this).apply {
            //facebookAppId = getString(R.string.facebook_app_id)
            //facebookPermissions = null
            //googleApiClient = null
        }

    }


    override fun onResume() {
        super.onResume()
        user = UserSessionManager.getCurrentUser(this) ?: return
        refreshLayout()
    }

    private fun refreshLayout() {
//        user = UserSessionManager.getCurrentUser(this) ?: return
//        if (user != null) {
//            setVisibilityToLoginViews(View.GONE)
//            logout_button.visibility = View.VISIBLE
//        } else {
//            setVisibilityToLoginViews(View.VISIBLE)
//            logout_button.visibility = View.GONE
//        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        login?.onActivityResult(requestCode, resultCode, data, config)
    }

    override fun onLoginSuccess(user: SmartUser) {
        toast(user.toString())
        refreshLayout()
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
    }

    override fun onLoginFailure(e: SmartLoginException) {
        toast(e.message!!)
        Log.i("SigninActivity", e.message)
    }

    override fun doCustomLogin() = SmartUser().apply {
        //email = email_edittext.text.toString()
        Log.i("SigninActivity", email)
    }

    override fun doCustomSignup() = SmartUser().apply {
        //email = email_edittext.text.toString()
        Log.i("SigninActivity", email)
    }

    private fun setListeners() {

        btn_sign_up.setOnClickListener({
            val intent = Intent(this, SignupActivity::class.java)
            startActivity(intent)
        })

    }

    private fun setVisibilityToLoginViews(visibility: Int) {

        password_edittext.visibility = visibility
    }


}
