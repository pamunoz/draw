package com.pfariasmunoz.drawingapp.ui.drawing


interface DrawingContract {
    interface View {
        val currentUserId: String
    }
    interface Presenter {
        fun setupView(view: View)
        fun findUser()
        fun updateUser()
    }
}