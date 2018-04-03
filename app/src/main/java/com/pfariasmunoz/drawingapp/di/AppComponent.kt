package com.pfariasmunoz.drawingapp.di

import android.content.Context
import com.pfariasmunoz.drawingapp.di.modules.ContextModule
import com.pfariasmunoz.drawingapp.di.modules.SingupModule
import com.pfariasmunoz.drawingapp.ui.signup.SignupContract
import dagger.Component

@Component(modules = [ContextModule::class, SingupModule::class])
interface AppComponent {
    fun appContext(): Context

    fun signupView(): SignupContract.View
}