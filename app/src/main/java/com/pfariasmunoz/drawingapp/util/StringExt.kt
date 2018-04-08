package com.pfariasmunoz.drawingapp.util

fun <T: String?> T?.exist(): Boolean {
    return (this.isNotNull() && this!!.length > 0)
}

fun Any?.isNotNull(): Boolean {
    return this != null
}