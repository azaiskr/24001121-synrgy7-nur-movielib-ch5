package com.synrgy.domain.model

data class MovieDetailModel(
    val posterPath: String,
    val title: String,
    val runtime: Int,
    val rate: String,
    val releaseDate: String,
    val genres: List<String>,
    val overview: String
)