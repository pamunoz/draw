package com.pfariasmunoz.drawingapp.ui.signup

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.pfariasmunoz.drawingapp.R
import com.pfariasmunoz.drawingapp.ui.signin.SigninActivity
import kotlinx.android.synthetic.main.activity_signin.*

class SignupActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)


    }

    private fun setListeners() {
        btn_sign_in.setOnClickListener({
            val intent = Intent(this, SigninActivity::class.java)
            startActivity(intent)
        })
    }
}
