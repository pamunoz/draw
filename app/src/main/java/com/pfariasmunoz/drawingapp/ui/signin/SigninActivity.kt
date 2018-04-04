package com.pfariasmunoz.drawingapp.ui.signin

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.pfariasmunoz.drawingapp.R
import com.pfariasmunoz.drawingapp.di.Injector
import com.pfariasmunoz.drawingapp.ui.signup.SignupActivity
import kotlinx.android.synthetic.main.activity_signin.*


class SigninActivity : AppCompatActivity(), SinginContract.View {

    val presenter: SigninPresenter

    init {
        this.presenter = Injector.get().signinPresenter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signin)
        setListeners()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun setListeners() {

        btn_sign_up.setOnClickListener({
            val intent = Intent(this, SignupActivity::class.java)
            startActivity(intent)
        })

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

}
