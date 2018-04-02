package com.pfariasmunoz.drawingapp.signin

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.pfariasmunoz.drawingapp.HomeActivity
import com.pfariasmunoz.drawingapp.R
import com.pfariasmunoz.drawingapp.signup.SignupActivity
import com.pfariasmunoz.drawingapp.util.toast
import kotlinx.android.synthetic.main.activity_signin.*
import studios.codelight.smartloginlibrary.*
import studios.codelight.smartloginlibrary.SmartLoginCallbacks
import studios.codelight.smartloginlibrary.users.SmartFacebookUser
import studios.codelight.smartloginlibrary.users.SmartGoogleUser
import studios.codelight.smartloginlibrary.users.SmartUser
import studios.codelight.smartloginlibrary.util.SmartLoginException


class SigninActivity : AppCompatActivity(), SmartLoginCallbacks {

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
//        facebook_login_button.setOnClickListener({
//            login = SmartLoginFactory.build(LoginType.Facebook).apply {
//                login(config)
//            }
//
//        })
//        google_login_button.setOnClickListener({
//            login = SmartLoginFactory.build(LoginType.Google).apply {
//                login(config)
//            }
//        })
//        custom_signin_button.setOnClickListener({
//            login = SmartLoginFactory.build(LoginType.CustomLogin).apply {
//                login(config)
//            }
//        })
//        custom_signup_button.setOnClickListener({
//            login = SmartLoginFactory.build(LoginType.CustomLogin).apply {
//                signup(config)
//            }
//        })
//
//        logout_button.setOnClickListener({
//            if (user != null) {
//                login = when(user) {
//                    is SmartFacebookUser -> {
//                        SmartLoginFactory.build(LoginType.Facebook)
//                    }
//                    is SmartGoogleUser -> {
//                        SmartLoginFactory.build(LoginType.Google)
//                    }
//                    else -> {
//                        val smartLogin = SmartLoginFactory.build(LoginType.CustomLogin)
//                        val result: Boolean = smartLogin.logout(this)
//                        if (result) refreshLayout()
//                        smartLogin
//                    }
//                }
//            }
//        })
    }

    private fun setVisibilityToLoginViews(visibility: Int) {
//        facebook_login_button.visibility = visibility
//        google_login_button.visibility = visibility
//        custom_signin_button.visibility = visibility
//        custom_signup_button.visibility = visibility
//        email_edittext.visibility = visibility
        password_edittext.visibility = visibility
    }


}
