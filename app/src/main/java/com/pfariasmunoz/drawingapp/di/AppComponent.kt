package com.pfariasmunoz.drawingapp.di

import android.content.Context
import com.pfariasmunoz.drawingapp.di.modules.ContextModule
import dagger.Component

@Component(modules = [ContextModule::class])
interface AppComponent {
    fun appContext(): Context
}