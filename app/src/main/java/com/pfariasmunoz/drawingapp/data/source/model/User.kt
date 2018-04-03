package com.pfariasmunoz.drawingapp.data.source.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import java.util.*

/**
 * Model class for a User.
 *
 * @param id id of the user
 * @param drawing is the bitmap of the view
 * @param password password of the user
 * @param login login of the user
 */
@Entity(tableName = "users")
data class User(
        @PrimaryKey(autoGenerate = true)
        @ColumnInfo(name = "_id") val id: Long,
        @ColumnInfo(name = "drawing", typeAffinity = ColumnInfo.BLOB) val drawing: ByteArray?,
        @ColumnInfo(name = "password") val password: String,
        @ColumnInfo(name = "login") var login: String) {

        override fun equals(other: Any?): Boolean {
                if (this === other) return true
                if (javaClass != other?.javaClass) return false

                other as User

                if (!Arrays.equals(drawing, other.drawing)) return false

                return true
        }

        override fun hashCode(): Int {
                return drawing?.let { Arrays.hashCode(it) } ?: 0
        }


}

