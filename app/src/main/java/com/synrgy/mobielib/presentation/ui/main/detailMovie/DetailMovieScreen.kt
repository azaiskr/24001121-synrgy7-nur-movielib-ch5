package com.synrgy.mobielib.presentation.ui.main.detailMovie

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
import com.synrgy.mobielib.R
import com.synrgy.mobielib.common.utils.Resource
import com.synrgy.mobielib.data.remote.response.MovieDetailResponse
import com.synrgy.mobielib.presentation.ui.components.ErrorScreen
import com.synrgy.mobielib.presentation.ui.components.LoadingScreen

@Composable
fun DetailMovieScreen(
    movieId: Int,
    viewModel: DetailMovieViewModel = hiltViewModel()
) {
    val movieDetail by viewModel.movieDetail.collectAsState()



    LaunchedEffect(Unit) {
        viewModel.getMovieDetail(movieId)
    }

    when (movieDetail) {
        is Resource.Loading -> {
            LoadingScreen()
            Text(text = "Loading...", color = Color.Gray, modifier = Modifier.fillMaxSize())
        }

        is Resource.Success -> {
            val data = (movieDetail as Resource.Success<MovieDetailResponse>).data
            Log.d("DetailMovieScreen", "Success: ${data.title}")
            DetailMovieContent(movie = data)
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

@Composable
fun DetailMovieContent(movie: MovieDetailResponse) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(16.dp)
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
            movie.title?.let {
                Text(
                    text = it,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }

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
                    text = "${movie.voteAverage} (IMDb)",
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
                    movie.releaseDate?.let {
                        Text(
                            text = it,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color.White
                        )
                    }
                }
                Column(horizontalAlignment = Alignment.End) {
                    Text(
                        text = "Genre",
                        fontSize = 14.sp,
                        color = Color.LightGray,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    Row {
                        movie.genres?.forEach { genre ->
                            if (genre != null) {
                                genre.name?.let {
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
                movie.overview?.let {
                    Text(
                        text = it,
                        fontSize = 16.sp,
                        color = Color.White,
                        textAlign = TextAlign.Justify
                    )
                }
            }
        }
    }
}