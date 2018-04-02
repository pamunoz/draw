package com.pfariasmunoz.drawingapp.data

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

/**
 * Model class for a User.
 *
 * @param id id of the user
 * @param password password of the user
 * @param login login of the user
 */
@Entity(tableName = "users")
data class User(
        @PrimaryKey(autoGenerate = true)
        @ColumnInfo(name = "entryId") var id: Long?,
        @ColumnInfo(name = "password") val password: String = "",
        @ColumnInfo(name = "login") var login: String = "")

/**
 * Model class for a Drawing.
 *
 * @param id id of the drawing
 * @param name name of the drawing
 * @param userId user id of the [User]
 */
@Entity(tableName = "drawings")
data class Drawing(
        @PrimaryKey(autoGenerate = true)
        var id: Long?,
        @ColumnInfo(name = "name") var name: String,
        @ColumnInfo(name = "userId")var userId: String)