package com.pfariasmunoz.drawingapp.util

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.util.DisplayMetrics
import android.util.TypedValue
import android.view.LayoutInflater
import android.widget.TextView
import android.widget.Toast
import com.pfariasmunoz.drawingapp.R
import org.jetbrains.anko.AlertDialogBuilder

fun Context.toast(message: String) = Toast.makeText(this, message, Toast.LENGTH_SHORT ).show()

/**
 * Extensions for simpler launching of Activities
 */

@SuppressLint("ObsoleteSdkInt")
inline fun <reified T : Any> Activity.launchActivity(
        requestCode: Int = -1,
        options: Bundle? = null,
        noinline init: Intent.() -> Unit = {}) {

    val intent = newIntent<T>(this)
    intent.init()
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
        startActivityForResult(intent, requestCode, options)
    } else {
        startActivityForResult(intent, requestCode)
    }
}

@SuppressLint("ObsoleteSdkInt")
inline fun <reified T : Any> Context.launchActivity(
        options: Bundle? = null,
        noinline init: Intent.() -> Unit = {}) {

    val intent = newIntent<T>(this)
    intent.init()
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
        startActivity(intent, options)
    } else {
        startActivity(intent)
    }
}

inline fun <reified T : Any> newIntent(context: Context): Intent =
        Intent(context, T::class.java)
// DeviceDimensionsHelper.getDisplayWidth(context) => (display width in pixels)
val Context.displayWidth: Int
    get() = this.resources.displayMetrics.widthPixels
// DeviceDimensionsHelper.getDisplayHeight(context) => (display height in pixels)
val Context.displayHeight: Int
    get() = this.resources.displayMetrics.heightPixels
// DeviceDimensionsHelper.convertDpToPixel(25f, context) => (25dp converted to pixels)
fun Context.dpToPixels(dp: Float): Float {
    return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, this.resources.displayMetrics)
}
// DeviceDimensionsHelper.convertPixelsToDp(25f, context) => (25px converted to dp)
fun Context.pixelsToDp(px: Float): Float {
    return px / (this.resources.displayMetrics.densityDpi / 160f)
}

val Context.preferences: SharedPreferences
    get() = this.getSharedPreferences("prefs", Context.MODE_PRIVATE)

fun <T>SharedPreferences.put(key: String, value: T) {
    when(value) {
        is String -> this.edit().putString(key, value).apply()
        is Boolean -> this.edit().putBoolean(key, value).apply()
        is Float -> this.edit().putFloat(key, value).apply()
        is Int -> this.edit().putInt(key, value).apply()
        is Long -> this.edit().putLong(key, value).apply()
    }
}

fun Context.okDialog(
        title: String? = null) {
    val dialogBuilder = AlertDialog.Builder(this)
    val inflater: LayoutInflater = LayoutInflater.from(this)
    val dialogView = inflater.inflate(R.layout.dialog_layout, null)
    val titleView = dialogView.findViewById<TextView>(R.id.dialog_title)
    val buttonView = dialogView.findViewById<TextView>(R.id.btn_dialog)
    titleView.text = title
    dialogBuilder.setView(dialogView)
    val dialog = dialogBuilder.create()
    buttonView.setOnClickListener({
        dialog.cancel()
    })
    dialog.show()
}
