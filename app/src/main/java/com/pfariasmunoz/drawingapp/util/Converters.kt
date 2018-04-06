package com.pfariasmunoz.drawingapp.util

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.nio.ByteBuffer


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
