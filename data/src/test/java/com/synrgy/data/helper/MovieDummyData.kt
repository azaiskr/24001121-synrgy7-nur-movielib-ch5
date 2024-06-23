package com.synrgy.data.helper

import com.synrgy.data.remote.response.GenresItem
import com.synrgy.data.remote.response.MovieDetailResponse
import com.synrgy.data.remote.response.MovieListResponse
import com.synrgy.data.remote.response.ProductionCompaniesItem
import com.synrgy.data.remote.response.ProductionCountriesItem
import com.synrgy.data.remote.response.ResultsItem
import com.synrgy.data.remote.response.SpokenLanguagesItem
import com.synrgy.domain.model.MovieListModel

val sampleResultsItem = ResultsItem(
    overview = "A thrilling action movie.",
    originalLanguage = "en",
    originalTitle = "Original Action Title",
    video = false,
    title = "Action Movie",
    genreIds = listOf(28, 12, 16),
    posterPath = "/path/to/poster.jpg",
    backdropPath = "/path/to/backdrop.jpg",
    releaseDate = "2024-06-01",
    popularity = 1234.56,
    voteAverage = 8.7,
    id = 1,
    adult = false,
    voteCount = 4567
)

val mockMovieListResponse = MovieListResponse(
    page = 1,
    totalPages = 10,
    results = listOf(sampleResultsItem),
    totalResults = 100
)

val mockMovieDetailResponse = MovieDetailResponse(
    originalLanguage = "en",
    imdbId = "tt1234567",
    video = false,
    title = "Title",
    backdropPath = "/path/to/backdrop.jpg",
    revenue = 1000000,
    genres = listOf(GenresItem(name = "Action", id = 28)),
    popularity = 1234.56,
    productionCountries = listOf(ProductionCountriesItem(name = "USA", iso31661 = "US")),
    id = 123,
    voteCount = 100,
    budget = 100000,
    overview = "Overview",
    originalTitle = "Original Title",
    runtime = 120,
    posterPath = "/path/to/poster.jpg",
    originCountry = listOf("USA"),
    spokenLanguages = listOf(
        SpokenLanguagesItem(
            name = "English",
            iso6391 = "en",
            englishName = "English"
        )
    ),
    productionCompanies = listOf(
        ProductionCompaniesItem(
            name = "Production Company",
            id = 1,
            logoPath = "/path/to/logo.jpg",
            originCountry = "USA"
        )
    ),
    releaseDate = "2021-01-01",
    voteAverage = 8.0,
    belongsToCollection = null,
    tagline = "Tagline",
    adult = false,
    homepage = "http://homepage.com",
    status = "Released"
)

val movieListModel = MovieListModel(
    id = 123,
    title = "Title",
    overview = "Overview",
    posterPath = "/path/to/poster.jpg"
)