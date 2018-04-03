package com.pfariasmunoz.drawingapp.data.source.local

import com.pfariasmunoz.drawingapp.data.LocalDataNotFoundException
import com.pfariasmunoz.drawingapp.data.Result
import com.pfariasmunoz.drawingapp.data.source.UsersDataSource
import com.pfariasmunoz.drawingapp.data.source.model.User
import com.pfariasmunoz.drawingapp.util.AppExecutors
import kotlinx.coroutines.experimental.withContext

class UsersLocalDataSource private constructor(
        val appExecutors: AppExecutors,
        val usersDao: UsersDao
) : UsersDataSource {


    override suspend fun getUsers(): Result<List<User>> = withContext(appExecutors.ioContext) {
        val users = usersDao.getUsers()
        if (users.isNotEmpty()) {
            Result.Success(usersDao.getUsers())
        } else {
            Result.Error(LocalDataNotFoundException())
        }
    }

    override suspend fun getUser(userId: Long): Result<User> = withContext(appExecutors.ioContext){
        val user = usersDao.getUserById(userId)
        if (user != null) Result.Success(user) else Result.Error(LocalDataNotFoundException())
    }

    override suspend fun saveUser(user: User) = withContext(appExecutors.ioContext){
        usersDao.insertUser(user)
    }

    override suspend fun deleteAllUsers() = withContext(appExecutors.ioContext) {
        usersDao.deleteUsers()
    }

    override suspend fun deleteUser(userId: Long) = withContext(appExecutors.ioContext) {
        usersDao.deleteUserById(userId)
    }

    override suspend fun updateUser(user: User): Int = withContext(appExecutors.ioContext) {
        usersDao.updateUser(user)
    }

}