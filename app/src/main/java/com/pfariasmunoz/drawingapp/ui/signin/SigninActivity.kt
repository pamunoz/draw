package com.pfariasmunoz.drawingapp.ui.signin

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.pfariasmunoz.drawingapp.R
import com.pfariasmunoz.drawingapp.data.source.model.User
import com.pfariasmunoz.drawingapp.di.Injector
import com.pfariasmunoz.drawingapp.ui.signup.SignupActivity
import com.pfariasmunoz.drawingapp.util.launchActivity
import kotlinx.android.synthetic.main.activity_signin.*


class SigninActivity : AppCompatActivity(), SinginContract.View {

    val presenter: SigninPresenter

    init {
        this.presenter = Injector.get().signinPresenter()
    }

    companion object {
        val REQUEST_CODE = 1
        val CURRENT_USER_ID = "current_user_id"
    }

    override var userId: String?
        get() = intent.extras.getString("userId")
        set(value) {

        }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signin)
        setListeners()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                userId = data?.getStringExtra(CURRENT_USER_ID)
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                userId = ""
            }
        }
    }

    private fun setListeners() {
        btn_sign_up.setOnClickListener({
            launchActivity<SignupActivity>(REQUEST_CODE)
        })

    }

}
