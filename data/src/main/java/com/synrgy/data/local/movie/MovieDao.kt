package com.synrgy.data.local.movie

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFavMovie(movie: MovieDataModel)

    @Delete
    suspend fun deleteFavMovie(movie: MovieDataModel)

    @Query("SELECT * FROM MovieDataModel")
    fun getAllFavMovie(): Flow<List<MovieDataModel>>

}