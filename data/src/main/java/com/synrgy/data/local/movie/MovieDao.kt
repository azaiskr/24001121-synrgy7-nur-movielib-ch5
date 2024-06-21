package com.synrgy.data.local.movie

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface MovieDao {

    //add fav movie
    @Insert
    suspend fun addFavMovie(movie: MovieDataModel)

    @Delete
    suspend fun deleteFavMovie(movie: MovieDataModel)

    @Query("SELECT * FROM MovieDataModel")
    suspend fun getAllFavMovie():List<MovieDataModel>

}