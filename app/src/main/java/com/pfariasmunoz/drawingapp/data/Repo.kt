package com.pfariasmunoz.drawingapp.data

import com.pfariasmunoz.drawingapp.data.source.UsersDataSource
import com.pfariasmunoz.drawingapp.data.source.model.User

/**
 * Concrete implementation to load users from the data sources into a cache.
 *
 *
 * For simplicity, this implements a dumb synchronisation between locally persisted data and data
 * obtained from the server, by using the remote data source only if the local database doesn't
 * exist or is empty.
 */
class Repo(
        val usersRemoteDataSource: UsersDataSource? = null,
        val usersLocalDataSource: UsersDataSource? = null
): UsersDataSource {

    /**
     * This variable has public visibility so it can be accessed from tests.
     */
    var cachedUsers: LinkedHashMap<Long?, User> = LinkedHashMap()

    /**
     * Marks the cache as invalid, to force an update the next time data is requested. This variable
     * has package local visibility so it can be accessed from tests.
     */
    var cacheIsDirty = false

    /**
     * Gets users from cache, local data source (SQLite) or remote data source, whichever is
     * available first.
     */
    override suspend fun getUsers(): Result<List<User>> {
        // Respond immediately with cache if available and not dirty
        if (cachedUsers.isNotEmpty() && !cacheIsDirty) {
            return Result.Success(cachedUsers.values.toList())
        }

        return if (cacheIsDirty) {
            // If the cache is dirty we need to fetch new data from the network.
            getUsersFromRemoteDataSource()
        } else {
            // Query the local storage if available. If not, query the network.
            val result = usersLocalDataSource?.getUsers()
            when (result) {
                is Result.Success -> {
                    refreshCache(result.data)
                    Result.Success(cachedUsers.values.toList())
                }
                else -> getUsersFromRemoteDataSource()
            }
        }
    }

    /**
     * Gets users from local data source (sqlite) unless the table is new or empty. In that case it
     * uses the network data source.
     */
    override suspend fun getUser(userId: Long): Result<User> {
        val userInCache = getUserWithId(userId)

        // Respond immediately with cache if available
        if (userInCache != null) return Result.Success(userInCache)

        // Load from server/persisted if needed.

        // Is the user in the local data source? If not, query the network.
        val localResult = usersLocalDataSource?.getUser(userId)
        return when (localResult) {
            is Result.Success -> Result.Success(cache(localResult.data))
            else -> {
                val remoteResult = usersRemoteDataSource?.getUser(userId)
                when (remoteResult) {
                    is Result.Success -> Result.Success(cache(remoteResult.data))
                    else -> Result.Error(RemoteDataNotFoundException())
                }
            }
        }
    }

    override suspend fun saveUser(user: User) {
        // Do in memory cache update to keep the app UI up to date
        cache(user).let {
            usersRemoteDataSource?.saveUser(it)
            usersLocalDataSource?.saveUser(it)
        }
    }

    override suspend fun deleteAllUsers() {
        usersRemoteDataSource?.deleteAllUsers()
        usersLocalDataSource?.deleteAllUsers()
        cachedUsers.clear()
    }

    override suspend fun deleteUser(userId: Long) {
        usersRemoteDataSource?.deleteUser(userId)
        usersLocalDataSource?.deleteUser(userId)
        cachedUsers.remove(userId)
    }

    override suspend fun refreshUsers() {
        cacheIsDirty = true
    }

    override suspend fun getDrawing(userId: Long) = getUserWithId(userId)?.drawing

    private suspend fun getUsersFromRemoteDataSource(): Result<List<User>> {
        val result = usersRemoteDataSource?.getUsers()
        return when (result) {
            is Result.Success -> {
                refreshCache(result.data)
                refreshLocalDataSource(result.data)
                Result.Success(ArrayList(cachedUsers.values))
            }
            else -> Result.Error(RemoteDataNotFoundException())
        }

    }

    private fun refreshCache(users: List<User>) {
        cachedUsers.clear()
        users.forEach {
            cache(it)
        }
        cacheIsDirty = false
    }

    private suspend fun refreshLocalDataSource(users: List<User>) {
        usersLocalDataSource?.deleteAllUsers()
        for (user in users) {
            usersLocalDataSource?.saveUser(user)
        }
    }

    private fun getUserWithId(id: Long) = cachedUsers[id]

    private fun cache(user: User): User {
        val cachedUser = User(user.id, user.drawing, user.login, user.password)
        cachedUsers[cachedUser.id] = cachedUser
        return cachedUser
    }

    companion object {

        private var INSTANCE: Repo? = null

        /**
         * Returns the single instance of this class, creating it if necessary.
         * @param usersRemoteDataSource the backend data source
         * *
         * @param usersLocalDataSource  the device storage data source
         * *
         * @return the [Repo] instance
         */
        @JvmStatic
        fun getInstance(usersRemoteDataSource: UsersDataSource,
                        usersLocalDataSource: UsersDataSource): Repo {
            return INSTANCE ?: Repo(usersRemoteDataSource, usersLocalDataSource)
                    .apply { INSTANCE = this }
        }

        /**
         * Used to force [getInstance] to create a new instance
         * next time it's called.
         */
        @JvmStatic
        fun destroyInstance() {
            INSTANCE = null
        }
    }

}