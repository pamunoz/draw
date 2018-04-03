package com.pfariasmunoz.drawingapp.di

import com.pfariasmunoz.drawingapp.App

object Injector {
    fun get(): AppComponent = App.INSTANCE.component
}