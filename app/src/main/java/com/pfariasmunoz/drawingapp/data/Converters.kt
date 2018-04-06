package com.pfariasmunoz.drawingapp.data

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.nio.ByteBuffer

object Converters {


    fun convertBitmapToByteArray(bitmap: Bitmap): ByteArray {
        var os: ByteArrayOutputStream? = null
        try {
            os = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, os)
            return os.toByteArray()
        } finally {
            if (os != null) {
                try {
                    os.close()
                } catch (e: IOException) {
                    Log.e(Converters::class.java.simpleName, "ByteArrayOutputStream was not closed")
                }
            }
        }
    }
    //@TypeConverter
    fun convertBitmapToByteArrayUncompressed(bitmap: Bitmap): ByteArray {
        val bf: ByteBuffer = ByteBuffer.allocate(bitmap.byteCount)
        bitmap.copyPixelsToBuffer(bf)
        bf.rewind()
        return  bf.array()
    }
    //@TypeConverter
    fun convertCompressedByteArrayToBitmap(byteArray: ByteArray): Bitmap {
        return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
    }
}