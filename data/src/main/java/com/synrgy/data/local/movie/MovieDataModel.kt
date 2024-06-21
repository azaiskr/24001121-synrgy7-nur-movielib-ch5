package com.synrgy.data.local.movie

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.synrgy.domain.model.MovieListModel
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
data class MovieDataModel(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Int,

    @ColumnInfo(name = "poster_path")
    val posterPath : String,

    @ColumnInfo(name = "title")
    val title : String,

    @ColumnInfo(name = "overview")
    val overview: String,
) : Parcelable

fun MovieDataModel.toMovieListModel() = MovieListModel(
    id = id,
    posterPath = posterPath,
    title = title,
    overview = overview
)

fun List<MovieDataModel>.toMovieList() = map { it.toMovieListModel() }

fun MovieListModel.toMovieDataModel() = MovieDataModel(
    id = id,
    posterPath = posterPath,
    title = title,
    overview = overview
)
