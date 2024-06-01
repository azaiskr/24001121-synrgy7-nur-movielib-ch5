package com.synrgy.mobielib.ui.main


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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.synrgy.mobielib.data.remote.response.MovieListResponse
import com.synrgy.mobielib.ui.components.ErrorScreen
import com.synrgy.mobielib.ui.components.LoadingScreen
import com.synrgy.mobielib.ui.components.MovieColumn
import com.synrgy.mobielib.ui.components.MovieRow
import com.synrgy.mobielib.utils.Response
import com.synrgy.mobielib.utils.ViewModelFactory

@Preview(showBackground = true)
@Composable
fun ListMovieScreen(
    modifier: Modifier = Modifier,
    onNavigateToDetail: (Int) -> Unit = {},
) {
    val viewModel: ListMovieViewModel = viewModel(
        factory = ViewModelFactory.getInstanceViewModel(
            context = LocalContext.current
        )
    )

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
                    is Response.Loading -> {
                        LoadingScreen()
                    }
                    is Response.Success -> {
                        val nowPlayingMovies =
                            (movieNowPlaying as Response.Success<MovieListResponse>).data
                        HorizontalMovieList(
                            movies = nowPlayingMovies,
                            modifier = modifier,
                            onNavigateToDetail = onNavigateToDetail
                        )
                    }

                    is Response.Error -> {
                        ErrorScreen(
                            exception = (movieNowPlaying as Response.Error).exception ?: "An unknown error occurred",
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
                    is Response.Loading -> {
                        LoadingScreen()
                    }
                    is Response.Success -> {
                        val topRatedMovies =
                            (movieTopRated as Response.Success<MovieListResponse>).data
                        HorizontalMovieList(
                            movies = topRatedMovies,
                            modifier = modifier,
                            onNavigateToDetail = onNavigateToDetail
                        )
                    }

                    is Response.Error -> {
                        ErrorScreen(
                            exception = (movieTopRated as Response.Error).exception ?: "An unknown error occurred",
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
            is Response.Loading -> {
                item{
                    LoadingScreen()
                }
            }
            is Response.Success -> {
                val mvPopular = (moviePopular as Response.Success<MovieListResponse>).data
                items(mvPopular.results.size) { index ->
                    Log.d("movie column", "ListMovieScreen: ${mvPopular.results[index].title}")
                    MovieColumn(
                        movies = mvPopular.results[index],
                        modifier = modifier,
                        onClick = { onNavigateToDetail(mvPopular.results[index].id) }
                    )
                }
            }

            is Response.Error -> {
                item{
                    ErrorScreen(
                        exception = (moviePopular as Response.Error).exception ?: "An unknown error occurred",
                        onClick = { viewModel.getMovieListPopular() }
                    )
                }
            }
        }
    }
}

@Composable
fun HorizontalMovieList(
    movies: MovieListResponse,
    modifier: Modifier,
    onNavigateToDetail: (Int) -> Unit,
) {
    val moviesData = movies.results
    LazyRow(
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(moviesData.size) { index ->
            Log.d("movie row", "HorizontalMovieList: ${moviesData[index].title}")
            MovieRow(
                moviesData[index],
                modifier = modifier,
                onClick = { onNavigateToDetail(moviesData[index].id) }
            )
        }
    }
}
