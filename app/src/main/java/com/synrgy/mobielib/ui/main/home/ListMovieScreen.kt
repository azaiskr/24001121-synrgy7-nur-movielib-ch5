package com.synrgy.mobielib.ui.main.home


import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.synrgy.common.Resource
import com.synrgy.mobielib.ui.components.ErrorScreen
import com.synrgy.mobielib.ui.components.LoadingScreen
import com.synrgy.mobielib.ui.components.MovieColumn
import com.synrgy.mobielib.ui.components.MovieRow
import com.synrgy.domain.model.MovieListModel

@Composable
fun ListMovieScreen(
    modifier: Modifier = Modifier,
    onNavigateToDetail: (Int) -> Unit = {},
    viewModel: ListMovieViewModel = hiltViewModel(),
) {

    val movieNowPlaying by viewModel.nowPlayingMovies.collectAsState()
    val movieTopRated by viewModel.topRatedMovies.collectAsState()
    val moviePopular by viewModel.popularMovies.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.getMovieListNowPlaying()
        viewModel.getMovieListTopRated()
        viewModel.getMovieListPopular()
    }

    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(bottom = 8.dp)
    ) {
        item {
            Column {
                Text(
                    text = "Movie lib",
                    fontSize = 24.sp,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onPrimary,
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                )

                Text(
                    text = "Now Playing",
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.onPrimary,
                    fontWeight = FontWeight.Medium,
                    modifier = modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                )
                when (movieNowPlaying) {
                    is Resource.Loading -> {
                        LoadingScreen()
                    }

                    is Resource.Success -> {
                        val nowPlayingMovies =
                            (movieNowPlaying as Resource.Success<List<MovieListModel>>).data
                        HorizontalMovieList(
                            movies = nowPlayingMovies,
                            modifier = modifier,
                            onNavigateToDetail = onNavigateToDetail
                        )
                    }

                    is Resource.Error -> {
                        ErrorScreen(
                            exception = (movieNowPlaying as Resource.Error).exception
                                ?: "An unknown error occurred",
                            onClick = { viewModel.getMovieListNowPlaying() }
                        )
                    }
                }
                Text(
                    text = "Top Rated",
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.onPrimary,
                    fontWeight = FontWeight.Medium,
                    modifier = modifier
                        .padding(horizontal = 16.dp)
                        .padding(top = 16.dp, bottom = 8.dp)
                )
                when (movieTopRated) {
                    is Resource.Loading -> {
                        LoadingScreen()
                    }

                    is Resource.Success -> {
                        val topRatedMovies =
                            (movieTopRated as Resource.Success<List<MovieListModel>>).data
                        HorizontalMovieList(
                            movies = topRatedMovies,
                            modifier = modifier,
                            onNavigateToDetail = onNavigateToDetail
                        )
                    }

                    is Resource.Error -> {
                        ErrorScreen(
                            exception = (movieTopRated as Resource.Error).exception
                                ?: "An unknown error occurred",
                            onClick = { viewModel.getMovieListTopRated() }
                        )
                    }
                }
                Text(
                    text = "Popular",
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.onPrimary,
                    fontWeight = FontWeight.Medium,
                    modifier = modifier
                        .padding(horizontal = 16.dp)
                        .padding(top = 16.dp, bottom = 8.dp)
                )
            }
        }
        when (moviePopular) {
            is Resource.Loading -> {
                item {
                    LoadingScreen()
                }
            }

            is Resource.Success -> {
                val mvPopular = (moviePopular as Resource.Success<List<MovieListModel>>).data
                items(mvPopular.size) { index ->
                    Log.d("movie column", "ListMovieScreen: ${mvPopular[index].title}")
                    MovieColumn(
                        movies = mvPopular[index],
                        modifier = modifier,
                        onClick = { onNavigateToDetail(mvPopular[index].id) }
                    )
                }
            }

            is Resource.Error -> {
                item {
                    ErrorScreen(
                        exception = (moviePopular as Resource.Error).exception
                            ?: "An unknown error occurred",
                        onClick = { viewModel.getMovieListPopular() }
                    )
                }
            }
        }
    }
}

@Composable
fun HorizontalMovieList(
    movies: List<MovieListModel>,
    modifier: Modifier,
    onNavigateToDetail: (Int) -> Unit,
) {
    LazyRow(
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(movies.size) { index ->
            Log.d("movie row", "HorizontalMovieList: ${movies[index].title}")
            MovieRow(
                movies[index],
                modifier = modifier,
                onClick = { onNavigateToDetail(movies[index].id) }
            )
        }
    }
}
