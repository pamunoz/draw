package com.pfariasmunoz.drawingapp.ui.userslist

interface UserListContract {
    interface View {
        fun showList()
    }

    interface Presenter {
        fun loadUsers()
    }
}