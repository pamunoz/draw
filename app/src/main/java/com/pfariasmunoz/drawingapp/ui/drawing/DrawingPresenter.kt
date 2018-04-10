package com.pfariasmunoz.drawingapp.ui.drawing

import com.pfariasmunoz.drawingapp.di.Injector
import com.pfariasmunoz.drawingapp.util.AppExecutors
import com.pfariasmunoz.drawingapp.util.launchSilent
import kotlinx.coroutines.experimental.async
import javax.inject.Inject

/**
 * This presenter is mostly in charge of async processes with coroutines
 */
@Suppress("JoinDeclarationAndAssignment")
class DrawingPresenter @Inject constructor(): DrawingContract.Presenter {

    /** The [DrawingContract.View] that make the ui calls */
    lateinit var view: DrawingContract.View
    /** The class that contains the coroutines context for the apps work*/
    private val appExecutors: AppExecutors

    init {
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