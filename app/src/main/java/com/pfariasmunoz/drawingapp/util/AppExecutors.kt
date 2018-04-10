package com.pfariasmunoz.drawingapp.util

import kotlinx.coroutines.experimental.DefaultDispatcher
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.newFixedThreadPoolContext
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.coroutines.experimental.CoroutineContext

const val THREAD_COUNT = 3

/**
 * Global executor pools for the whole application.
 *
 * Grouping tasks like this avoids the effects of task starvation (e.g. disk reads don't wait behind
 * webservice requests).
 */
@Singleton
class AppExecutors @Inject constructor(){
    val ioContext: CoroutineContext = DefaultDispatcher
    val networkContext: CoroutineContext = newFixedThreadPoolContext(THREAD_COUNT, "networkIO")
    val uiContext: CoroutineContext = UI
}
