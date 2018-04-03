package com.pfariasmunoz.drawingapp.data.source.local

import android.arch.persistence.room.Room
import android.support.test.InstrumentationRegistry
import android.support.test.filters.LargeTest
import android.support.test.runner.AndroidJUnit4
import android.util.Log
import com.pfariasmunoz.drawingapp.data.Result
import com.pfariasmunoz.drawingapp.data.source.model.User
import com.pfariasmunoz.drawingapp.util.SingleExecutors
import com.pfariasmunoz.drawingapp.util.runBlockingSilent
import org.hamcrest.CoreMatchers
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.instanceOf
import org.junit.After
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*
import org.junit.runner.RunWith
import java.util.*

@RunWith(AndroidJUnit4::class)
@LargeTest
class UsersLocalDataSourceTest {

    private lateinit var localDataSource: UsersLocalDataSource
    private lateinit var database: UsersAppDatabase

    @Before
    fun setUp() {
        // using an in-memory database for testing, since it doesn't survive killing the process
        database = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getTargetContext(),
                UsersAppDatabase::class.java).build()
        // Make sure that we're not keeping a reference to the wrong instance.
        UsersLocalDataSource.clearInstance()
        localDataSource = UsersLocalDataSource.getInstance(SingleExecutors(), database.usersDao())
    }

    @After
    fun tearDown() {
        database.close()
        UsersLocalDataSource.clearInstance()
    }

    @Test
    fun testPreconditions() {
        assertNotNull(localDataSource)
    }

    @Test
    fun saveUser_retrieveUser() = runBlockingSilent {
        // Given a new User
        val newUser = User(drawing = ByteArray(10),login = "Pablo", password = "iklru667")
        with(localDataSource) {
            saveUser(newUser)
            // Then the user can be retrieved from the persistent repository
            val result = getUser(newUser.id)
            assertThat(result, instanceOf(Result.Success::class.java))
            if (result is Result.Success) {
                assertThat(result.data, `is`(newUser))
            }
        }
    }

    @Test
    fun deleteAllUsers_emptyListOfRetrievedUser() = runBlockingSilent {
        with(localDataSource) {
            // Given a new user in the persistent repository and a mocked callback
            val newUser = User(drawing = ByteArray(10),login = "Pablo", password = "667ruikl")
            saveUser(newUser)

            // When all users are deleted
            deleteAllUsers()

            // Then the retrieved tasks is an empty list
            val result = getUsers()
            assertThat(result, instanceOf(Result.Error::class.java))
        }
    }

    @Test
    fun getUsers_retrieveSavedUsers() = runBlockingSilent {
        // Given 2 new tasks in the persistent repository
        with(localDataSource) {
            val user1 = User(drawing = ByteArray(10),login = "Person1", password = "111qqq")
            val user2 = User(drawing = ByteArray(10),login = "Person2", password = "222www")

            saveUser(user1)
            saveUser(user2)

            // Then the tasks can be retrieved from the persistent repository
            val result = getUsers()
            assertThat(result, CoreMatchers.instanceOf(Result.Success::class.java))
            if (result is Result.Success) {
                assertNotNull(result.data)
                assertTrue(result.data.size >= 2)

                var user1IdFount = false
                var user2IdFount = false
                for (user in result.data) {
                    if (user.id == user1.id) {
                        user1IdFount = true
                    }
                    if (user.id == user2.id) {
                        user2IdFount = true
                    }
                }

                Log.i("USER 1 ID: ", "ID = ${user1.id}")
                Log.i("USER 2 ID: ", "ID = ${user2.id}")

                assertTrue(user1IdFount)
                assertTrue(user2IdFount)
            }
        }

    }

}