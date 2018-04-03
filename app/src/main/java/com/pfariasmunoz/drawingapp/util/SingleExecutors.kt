package com.pfariasmunoz.drawingapp.util


import kotlin.coroutines.experimental.EmptyCoroutineContext

/**
 * Allow instant execution of tasks.
 */
class SingleExecutors : AppExecutors(EmptyCoroutineContext, EmptyCoroutineContext, EmptyCoroutineContext)