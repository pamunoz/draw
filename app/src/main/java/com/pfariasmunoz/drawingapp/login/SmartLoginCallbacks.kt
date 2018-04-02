package com.pfariasmunoz.drawingapp.login

import studios.codelight.smartloginlibrary.users.SmartUser
import studios.codelight.smartloginlibrary.util.SmartLoginException

interface SmartLoginCallbacks {
    fun onLoginSuccess(user: SmartUser)
    fun onLoginFailure(e: SmartLoginException)
    fun doCustomLogin(): SmartUser
    fun doCustomSignup(): SmartUser
}