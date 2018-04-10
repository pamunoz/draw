package com.pfariasmunoz.drawingapp.di.modules

import android.content.Context
import android.content.SharedPreferences
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
import kotlin.coroutines.experimental.CoroutineContext

@Module
class DatabaseModule {

    @Provides
    fun provideAppDatabase(context: Context): UsersAppDatabase =
            UsersAppDatabase.getInstance(context)

    @Provides
    fun provideUsersDao(database: UsersAppDatabase): UsersDao = database.usersDao()

    @Provides
    fun provideUsersLocalDataSource(appExecutors: AppExecutors, usersDao: UsersDao): UsersLocalDataSource = UsersLocalDataSource.getInstance(appExecutors, usersDao)

    @Provides
    fun provideAppExecutors(): AppExecutors = AppExecutors()


}