package com.pfariasmunoz.drawingapp.ui.signup

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.pfariasmunoz.drawingapp.R
import com.pfariasmunoz.drawingapp.ui.signin.SigninActivity
import kotlinx.android.synthetic.main.activity_signin.*

class SignupActivity : AppCompatActivity(), SignupContract.View {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)
    }

    override fun getUserName(): String {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getFirstPassword(): String {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getSecondPassword(): String {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun signup() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }



    private fun setListeners() {
        btn_sign_in.setOnClickListener({
            val intent = Intent(this, SigninActivity::class.java)
            startActivity(intent)
        })
    }
}
