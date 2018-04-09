package com.pfariasmunoz.drawingapp.data.source.local

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import com.pfariasmunoz.drawingapp.data.source.model.User

@Database(entities = [User::class], version = 2, exportSchema = false)
abstract class UsersAppDatabase : RoomDatabase() {

    abstract fun usersDao(): UsersDao

    companion object {

        private var INSTANCE: UsersAppDatabase? = null

        private val lock = Any()

        fun getInstance(context: Context): UsersAppDatabase {
            synchronized(lock) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                            UsersAppDatabase::class.java, "users.db")
                            .fallbackToDestructiveMigration()
                            .build()
                }
                return INSTANCE!!
            }
        }
    }
}