package com.pfariasmunoz.drawingapp.data.source.model

import android.arch.persistence.room.*
import android.graphics.Bitmap
import java.util.*

/**
 * Model class for a User.
 *
 * @param id id of the user
 * @param drawing is the bitmap of the view
 * @param password password of the user
 * @param login login of the user
 */
@Entity(tableName = "users", indices = [(Index(value = ["login"], unique = true))])
data class User(
        @PrimaryKey
        @ColumnInfo(name = "_id") var id: String = UUID.randomUUID().toString(),
        @ColumnInfo(name = "password") var password: String,
        @ColumnInfo(name = "login") var login: String)

