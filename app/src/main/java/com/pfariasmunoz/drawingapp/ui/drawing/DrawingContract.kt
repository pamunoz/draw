package com.pfariasmunoz.drawingapp.ui.drawing

import android.graphics.Bitmap


interface DrawingContract {
    interface View {
        val currentUserId: String
        fun draw(bitmap: Bitmap)

    }
    interface Presenter {
        fun setupView(view: View)
        fun loadUserDrawing()
        fun saveUser()
        fun saveBitmap(bitmap: Bitmap)
    }
}