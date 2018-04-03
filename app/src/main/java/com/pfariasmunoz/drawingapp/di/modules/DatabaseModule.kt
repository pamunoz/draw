package com.pfariasmunoz.drawingapp.di.modules

import android.arch.persistence.room.Room
import android.content.Context
import com.pfariasmunoz.drawingapp.data.source.UsersDataSource
import com.pfariasmunoz.drawingapp.data.source.local.UsersAppDatabase
import com.pfariasmunoz.drawingapp.data.source.local.UsersDao
import com.pfariasmunoz.drawingapp.data.source.local.UsersLocalDataSource
import com.pfariasmunoz.drawingapp.util.AppExecutors
import com.pfariasmunoz.drawingapp.util.THREAD_COUNT
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.experimental.DefaultDispatcher
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.newFixedThreadPoolContext
import javax.inject.Named
import kotlin.coroutines.experimental.CoroutineContext

@Module
class DatabaseModule {

    @Provides @Named("uiContext")
    fun provideCoroutineUIContext(): CoroutineContext = UI

    @Provides @Named("dispatcherContext")
    fun provideCoroutineDefaultDispatcherContext(): CoroutineContext = DefaultDispatcher

    @Provides @Named("networkIOContext")
    fun provideCoroutineNetworkIOContext(): CoroutineContext = newFixedThreadPoolContext(THREAD_COUNT, "networkIO")

    @Provides
    fun provideAppExecutors(
            ioContext: CoroutineContext = DefaultDispatcher,
            networkContext: CoroutineContext = newFixedThreadPoolContext(THREAD_COUNT, "networkIO"),
            uiContext: CoroutineContext = UI): AppExecutors = AppExecutors(ioContext, networkContext, uiContext)

    @Provides
    fun provideAppDatabase(context: Context): UsersAppDatabase =
            UsersAppDatabase.getInstance(context)

    @Provides
    fun provideUsersDao(database: UsersAppDatabase): UsersDao = database.usersDao()

    @Provides
    fun userLocalDataSource(executors: AppExecutors, userDao: UsersDao): UsersDataSource =
            UsersLocalDataSource.getInstance(executors, userDao)
}