package com.pfariasmunoz.drawingapp.di.modules

import android.content.Context
import dagger.Module
import dagger.Provides

@Module
class ContextModule(val context: Context) {

    @Provides
    fun appContext(): Context = context
}