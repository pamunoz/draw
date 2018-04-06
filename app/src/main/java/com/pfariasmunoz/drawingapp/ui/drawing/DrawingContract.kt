package com.pfariasmunoz.drawingapp.ui.drawing

import android.graphics.Bitmap


interface DrawingContract {
    interface View {
        val currentUserId: String
        fun getDrawing(): Bitmap
        fun draw(bitmap: Bitmap)
        fun saveUserDrawing()

    }
    interface Presenter {
        fun setupView(view: View)
        fun loadUserDrawing()
        fun saveUser()

    }
}