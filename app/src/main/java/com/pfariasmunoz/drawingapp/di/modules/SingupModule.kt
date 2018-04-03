package com.pfariasmunoz.drawingapp.di.modules

import com.pfariasmunoz.drawingapp.ui.signup.SignupActivity
import com.pfariasmunoz.drawingapp.ui.signup.SignupContract
import dagger.Module
import dagger.Provides

@Module
class SingupModule {
    @Provides
    fun provideSignupView(view: SignupActivity): SignupContract.View = view
}