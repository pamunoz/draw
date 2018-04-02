package com.pfariasmunoz.drawingapp.data.source.local

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import com.pfariasmunoz.drawingapp.data.source.model.User

@Database(entities = [User::class], version = 1)
abstract class DrawingAppDatabase : RoomDatabase() {
    abstract fun getUsersDao(): UsersDao

    companion object {
        private var INSTANCE: DrawingAppDatabase? = null
        private val lock = Any()
        fun getInstance(context: Context): DrawingAppDatabase {
            synchronized(lock) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                            DrawingAppDatabase::class.java, "drawings.db")
                            .build()
                }
                return INSTANCE!!
            }
        }
    }
}