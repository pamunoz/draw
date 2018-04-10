package com.pfariasmunoz.drawingapp.ui.drawing

interface DrawingContract {
    interface View {
        val currentUserId: String
        fun saveDrawing()
        fun loadDrawing()
        fun eraseDrawing()
    }
    interface Presenter {
        fun setupView(view: View)
        fun saveBitmap()
        fun loadBitmap()
        fun eraseBitmap()
    }
}