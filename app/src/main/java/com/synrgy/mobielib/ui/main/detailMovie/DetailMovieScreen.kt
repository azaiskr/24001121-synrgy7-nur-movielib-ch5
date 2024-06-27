package com.synrgy.mobielib.ui.main.detailMovie

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.google.firebase.Firebase
import com.google.firebase.perf.performance
import com.synrgy.common.Resource
import com.synrgy.domain.model.MovieDetailModel
import com.synrgy.domain.model.MovieListModel
import com.synrgy.mobielib.R
import com.synrgy.mobielib.ui.components.ErrorScreen
import com.synrgy.mobielib.ui.components.LoadingScreen
import com.synrgy.mobielib.ui.components.MainButton

@Composable
fun DetailMovieScreen(
    movieId: Int,
    viewModel: DetailMovieViewModel = hiltViewModel(),
) {
    val movieDetail by viewModel.movieDetail.collectAsState()
    val favMovies by viewModel.favMovies.collectAsState()
    val isFav: Boolean = favMovies.any { it.id == movieId }
    val trace = remember {
        Firebase.performance.newTrace("load_detail_movie_trace")
    }

    LaunchedEffect(Unit) {
        trace.start()
        viewModel.getMovieDetail(movieId)
        trace.stop()
    }

    when (movieDetail) {
        is Resource.Loading -> {
            LoadingScreen()
            Text(text = "Loading...", color = Color.Gray, modifier = Modifier.fillMaxSize())
        }

        is Resource.Success -> {
            val data = (movieDetail as Resource.Success<MovieDetailModel>).data
            Log.d("DetailMovieScreen", "Success: ${data.title}")

            val movie = MovieListModel(
                id = movieId,
                title = data.title,
                posterPath = data.posterPath,
                overview = data.overview,
            )

            DetailMovieContent(
                movie = data,
                isFav = isFav,
                onClick = {
                    if (isFav) {
                        viewModel.deleteFavMovie(movie)
                        Log.d("DetailMovieScreen", "DetailMovieContent: Btn Clicked")
                    } else {
                        viewModel.addFavMovie(movie)
                        Log.d("DetailMovieScreen", "DetailMovieContent: Btn Clicked")
                    }
                },
            )
        }

        is Resource.Error -> {
            ErrorScreen(
                exception = (movieDetail as Resource.Error).exception
                    ?: "An unknown error occurred",
                onClick = { viewModel.getMovieDetail(movieId) }
            )
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun DetailMovieContent(
    movie: MovieDetailModel,
    isFav: Boolean,
    onClick:() -> Unit,
) {
    val rememberScrollState = rememberScrollState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(16.dp)
                .verticalScroll(rememberScrollState),
        ) {
            Image(
                painter = rememberAsyncImagePainter(model = "https://image.tmdb.org/t/p/w500${movie.posterPath}"),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .clip(RoundedCornerShape(16.dp))
                    .height(300.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = movie.title,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(24.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(
                    modifier = Modifier.size(16.dp),
                    painter = painterResource(id = R.drawable.baseline_access_time),
                    contentDescription = null,
                    tint = Color.LightGray
                )
                Text(
                    text = "${movie.runtime} minutes",
                    fontSize = 14.sp,
                    color = Color.LightGray,
                    modifier = Modifier.padding(start = 8.dp)
                )
                Spacer(modifier = Modifier.width(16.dp))
                Icon(
                    modifier = Modifier.size(16.dp),
                    imageVector = Icons.Default.Star,
                    contentDescription = null,
                    tint = Color.White
                )
                Text(
                    text = "${movie.rate} (IMDb)",
                    fontSize = 14.sp,
                    modifier = Modifier.padding(start = 8.dp),
                    color = Color.White
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(horizontalAlignment = Alignment.Start) {
                    Text(
                        text = "Release date",
                        fontSize = 14.sp,
                        color = Color.LightGray,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    Text(
                        text = movie.releaseDate,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.White
                    )
                }
                Column(horizontalAlignment = Alignment.End) {
                    Text(
                        text = "Genre",
                        fontSize = 14.sp,
                        color = Color.LightGray,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    FlowRow (
                        horizontalArrangement = Arrangement.spacedBy(2.dp),
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        movie.genres.forEach { genre ->
                            genre.let {
                                Text(
                                    text = it,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Medium,
                                    color = Color.White,
                                    modifier = Modifier
                                        .background(Color.Gray, RoundedCornerShape(8.dp))
                                        .padding(horizontal = 8.dp, vertical = 4.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
            Column(
                horizontalAlignment = Alignment.Start,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Synopsis",
                    fontSize = 14.sp,
                    color = Color.LightGray,
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = movie.overview,
                    fontSize = 16.sp,
                    color = Color.White,
                    textAlign = TextAlign.Justify
                )
            }
            Spacer(modifier = Modifier.height(24.dp))
            if (isFav) {
                MainButton(
                    modifier = Modifier,
                    onClick = {
                        onClick()
                    },
                    outlined = true,
                    labelText = "Remove from Favourites"
                )
            } else {
                MainButton(
                    modifier = Modifier,
                    onClick = {
                        onClick()
                    },
                    outlined = false,
                    labelText = "Add to Favourites"
                )
            }
        }
    }
}