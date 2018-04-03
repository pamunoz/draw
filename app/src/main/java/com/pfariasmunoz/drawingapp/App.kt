package com.pfariasmunoz.drawingapp

import android.app.Application
import com.pfariasmunoz.drawingapp.di.AppComponent
import com.pfariasmunoz.drawingapp.di.DaggerAppComponent
import com.pfariasmunoz.drawingapp.di.modules.ContextModule

class App: Application() {
    companion object {
        @JvmStatic lateinit var INSTANCE: App
            private set
    }

    lateinit var component: AppComponent
        private set

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
        component = DaggerAppComponent.builder()
                .contextModule(ContextModule(this))
                .build()
    }
}