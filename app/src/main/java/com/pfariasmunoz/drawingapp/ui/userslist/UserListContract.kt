package com.pfariasmunoz.drawingapp.ui.userslist

interface UserListContract {
    interface View {
        //fun showList()
        fun setupAdapter()
    }

    interface Presenter {
        fun setupView(view: View)
        fun loadUsers()
    }
}