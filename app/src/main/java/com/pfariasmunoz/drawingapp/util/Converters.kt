package com.pfariasmunoz.drawingapp.util

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.nio.ByteBuffer


val Bitmap.toByteArray : ByteArray
    get(){
    var os: ByteArrayOutputStream? = null
    try {
        os = ByteArrayOutputStream()
        this.compress(Bitmap.CompressFormat.PNG, 100, os)
        return os.toByteArray()
    } finally {
        if (os != null) {
            try {
                os.close()
            } catch (e: IOException) {
                Log.i("Bitmap to bytearray", e.message)
            }
        }
    }
}

val ByteArray.toBitmap: Bitmap
    get() = BitmapFactory.decodeByteArray(this, 0, this.size)
