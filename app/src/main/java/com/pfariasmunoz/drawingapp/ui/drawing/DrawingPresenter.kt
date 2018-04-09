package com.pfariasmunoz.drawingapp.ui.drawing

import android.graphics.Bitmap
import android.os.Environment
import android.widget.Toast
import com.pfariasmunoz.drawingapp.data.Result
import com.pfariasmunoz.drawingapp.data.source.local.UsersLocalDataSource
import com.pfariasmunoz.drawingapp.data.source.model.User
import com.pfariasmunoz.drawingapp.di.Injector
import com.pfariasmunoz.drawingapp.util.isNotNull
import com.pfariasmunoz.drawingapp.util.launchSilent
import com.pfariasmunoz.drawingapp.util.toBitmap
import com.pfariasmunoz.drawingapp.util.toByteArray
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.async
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import javax.inject.Inject
import kotlin.coroutines.experimental.CoroutineContext

class DrawingPresenter @Inject constructor(): DrawingContract.Presenter {

    lateinit var view: DrawingContract.View
    private val bgContext: CoroutineContext = CommonPool
    val usersDataSource : UsersLocalDataSource
    val uiContext: CoroutineContext

    init {
        this.usersDataSource = Injector.get().localUsersDataSource()
        this.uiContext = Injector.get().coroutineUIContext()
    }

    override fun setupView(view: DrawingContract.View) {
        this.view = view
    }


    override fun saveBitmap(bitmap: Bitmap) {
        val file = Environment.getExternalStorageDirectory()
        val newFile = File(file, "drawing.jpg")

        try {
            val fileOutputStream = FileOutputStream(newFile)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream)
            fileOutputStream.flush()
            fileOutputStream.close()

        } catch (e: FileNotFoundException) {
            e.printStackTrace()

        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}