package com.synrgy.mobielib.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.synrgy.domain.model.MovieListModel


@Composable
fun MovieRow(
    movie : MovieListModel,
    modifier: Modifier,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier
            .size(width = 100.dp, height = 160.dp),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.Black
        ),
        onClick = onClick
    ) {
        Box(
            modifier = modifier
                .fillMaxSize()
        ) {
            Image(
                painter = rememberAsyncImagePainter(model = "https://image.tmdb.org/t/p/w500${movie.posterPath}"),
                contentDescription = movie.title,
                contentScale = ContentScale.Crop,
                modifier = modifier.fillMaxSize()
            )
        }

    }
}
