package com.pfariasmunoz.drawingapp

import timber.log.Timber

class NotLoggingTree : Timber.Tree() {
    override fun log(priority: Int, tag: String?, message: String?, t: Throwable?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}