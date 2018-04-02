package com.pfariasmunoz.drawingapp.data.source.local

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
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
        @ColumnInfo(name = "_id") var id: Long?,
        @ColumnInfo(name = "drawing", typeAffinity = ColumnInfo.BLOB) val drawing: ByteArray? = null,
        @ColumnInfo(name = "password") val password: String = "",
        @ColumnInfo(name = "login") var login: String = "")

