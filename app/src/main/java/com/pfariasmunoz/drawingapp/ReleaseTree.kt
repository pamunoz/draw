package com.pfariasmunoz.drawingapp

import android.util.Log
import timber.log.Timber

class ReleaseTree: Timber.Tree() {

    companion object {
        private const val MAX_LOG_LENGTH = 4000
    }


    override fun isLoggable(tag: String?, priority: Int): Boolean {
        // Only WARN, ERROR or INFO
        return (priority != Log.VERBOSE || priority != Log.DEBUG || priority != Log.INFO)
    }

    override fun log(priority: Int, tag: String?, message: String?, t: Throwable?) {
        if (isLoggable(null, priority)) {
            if (message!!.length < MAX_LOG_LENGTH) {
                when(priority) {
                    Log.ASSERT -> Log.wtf(tag, message)
                    else -> Log.println(priority, tag, message)
                }
                return
            }
            for( i in message.indices) {
                var newLine = message.indexOf("\n", i)
                if (newLine == -1) {
                    newLine = message.length
                }
                var index = i
                do {
                    val end: Int = Math.min(newLine, index + MAX_LOG_LENGTH)
                    val part = message.substring(index, end)
                    if (priority == Log.ASSERT) Log.wtf(tag, part) else Log.println(priority, tag, part)
                    index = end
                } while (index < newLine)

            }
        }
    }
}