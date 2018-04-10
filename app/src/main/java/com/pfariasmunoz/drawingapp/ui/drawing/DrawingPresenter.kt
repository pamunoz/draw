package com.pfariasmunoz.drawingapp.ui.drawing

import android.graphics.Bitmap
import android.os.Environment
import android.widget.Toast
import com.pfariasmunoz.drawingapp.data.Result
import com.pfariasmunoz.drawingapp.data.source.local.UsersLocalDataSource
import com.pfariasmunoz.drawingapp.data.source.model.User
import com.pfariasmunoz.drawingapp.di.Injector
import com.pfariasmunoz.drawingapp.util.*
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.async
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import javax.inject.Inject
import kotlin.coroutines.experimental.CoroutineContext

@Suppress("JoinDeclarationAndAssignment")
class DrawingPresenter @Inject constructor(): DrawingContract.Presenter {

    lateinit var view: DrawingContract.View
    private val usersDataSource : UsersLocalDataSource
    private val appExecutors: AppExecutors

    init {
        this.usersDataSource = Injector.get().localUsersDataSource()
        this.appExecutors = Injector.get().appExecutors()
    }

    override fun setupView(view: DrawingContract.View) {
        this.view = view
    }


    override fun saveBitmap() = launchSilent(appExecutors.uiContext) {
        async(appExecutors.ioContext) {
           view.saveDrawing()
        }
    }

    override fun loadBitmap() = launchSilent(appExecutors.uiContext) {
        async(appExecutors.ioContext) {
            view.loadDrawing()
        }
    }

    override fun eraseBitmap() = launchSilent(appExecutors.uiContext) {
        async(appExecutors.ioContext) {
            view.eraseDrawing()
        }
    }
}