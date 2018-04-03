package com.pfariasmunoz.drawingapp.data.source.local

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import com.pfariasmunoz.drawingapp.data.source.model.User

@Database(entities = [User::class], version = 1, exportSchema = false)
abstract class UsersAppDatabase : RoomDatabase() {
    abstract fun getUsersDao(): UsersDao

    companion object {
        var testMode = false
        private val databaseName = "users.db"
        private var db: UsersAppDatabase? = null
        private var dbInstance: UsersDao? = null

        private val lock = Any()
        fun getInstance(context: Context): UsersDao {
            synchronized(lock) {
                if (dbInstance == null) {
                    if (testMode) {
                        db = Room.inMemoryDatabaseBuilder(context, UsersAppDatabase::class.java)
                                .allowMainThreadQueries().build()
                        dbInstance = db?.getUsersDao()
                    } else {
                        db = Room.databaseBuilder(context.applicationContext,
                                UsersAppDatabase::class.java, databaseName)
                                .build()
                        dbInstance = db?.getUsersDao()
                    }
                }
                return dbInstance!!
            }
        }
        private fun close() {
            db?.close()
        }
    }
}