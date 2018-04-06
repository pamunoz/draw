package com.pfariasmunoz.drawingapp.ui.drawing


interface DrawingContract {
    interface View {

    }
    interface Presenter {
        val currentUserId: String
        fun setupView(view: View)
        fun findUser()
    }
}