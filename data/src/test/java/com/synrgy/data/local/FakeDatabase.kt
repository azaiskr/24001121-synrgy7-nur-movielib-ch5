package com.synrgy.data.local

import androidx.room.DatabaseConfiguration
import androidx.room.InvalidationTracker
import androidx.sqlite.db.SupportSQLiteOpenHelper
import com.synrgy.data.local.movie.FakeMovieDao
import com.synrgy.data.local.movie.MovieDao
import com.synrgy.data.local.user.FakeUserDao
import com.synrgy.data.local.user.UserDao

class FakeDatabase (
    private val fakeUserDao: FakeUserDao,
    private val fakeMovieDao: FakeMovieDao
) : DatabaseImpl() {
    override fun userDao(): UserDao {
        return fakeUserDao
    }

    override fun movieDao(): MovieDao {
        return fakeMovieDao
    }

    override fun clearAllTables() {
        return Unit
    }

    override fun createInvalidationTracker(): InvalidationTracker {
        return InvalidationTracker(this)
    }

    override fun createOpenHelper(config: DatabaseConfiguration): SupportSQLiteOpenHelper {
        throw UnsupportedOperationException("Not implemented")
    }
}