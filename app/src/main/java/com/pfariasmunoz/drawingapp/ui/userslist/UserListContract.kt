package com.pfariasmunoz.drawingapp.ui.userslist

/** The distribution of work between the view and the presenter */
interface UserListContract {
    interface View {
        fun setupAdapter()
    }

    interface Presenter {
        fun setupView(view: View)
        fun loadUsers()
    }
}