package com.pfariasmunoz.drawingapp.ui.drawing

import android.graphics.Bitmap


interface DrawingContract {
    interface View {
        val currentUserId: String
    }
    interface Presenter {
        fun setupView(view: View)
        fun saveBitmap(bitmap: Bitmap)
    }
}