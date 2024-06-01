package com.synrgy.mobielib.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.synrgy.mobielib.data.remote.response.ResultsItem


@Composable
fun MovieColumn(
    movies: ResultsItem,
    modifier: Modifier,
    onClick: () -> Unit,
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 18.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.1f)
        ),
    ) {
        Row(
            modifier = modifier
                .fillMaxSize()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = rememberAsyncImagePainter(model = "https://image.tmdb.org/t/p/w500${movies.posterPath}"),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = modifier
                    .clip(RoundedCornerShape(4.dp))
                    .width(60.dp)
                    .height(80.dp)
            )
            Column(
                modifier = modifier
                    .padding(start = 8.dp)
            ) {
                Text(
                    text = movies.title,
                    fontSize = 18.sp,
                    lineHeight = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    maxLines = 2,
                    color = MaterialTheme.colorScheme.onPrimary.copy(0.8f),
                    overflow = TextOverflow.Ellipsis,
                    modifier = modifier.padding(bottom = 8.dp)
                )
                Text (
                    text = movies.overview,
                    fontSize = 12.sp,
                    maxLines = 3,
                    color = MaterialTheme.colorScheme.onPrimary.copy(0.5f),
                    textAlign = TextAlign.Justify,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}