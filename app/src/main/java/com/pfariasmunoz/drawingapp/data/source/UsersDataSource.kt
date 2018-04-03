package com.pfariasmunoz.drawingapp.data.source

import com.pfariasmunoz.drawingapp.data.Result
import com.pfariasmunoz.drawingapp.data.source.model.User

interface UsersDataSource {
    suspend fun getUsers(): Result<List<User>>
    suspend fun getUser(userId: Long): Result<User>
    suspend fun saveUser(user: User)
    suspend fun deleteAllUsers()
    suspend fun deleteUser(userId: Long): Int
    suspend fun updateUser(user: User): Int

}