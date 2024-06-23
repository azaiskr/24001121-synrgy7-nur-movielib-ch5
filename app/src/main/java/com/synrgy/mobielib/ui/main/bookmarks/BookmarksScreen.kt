package com.synrgy.mobielib.ui.main.bookmarks

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.synrgy.mobielib.R
import com.synrgy.mobielib.ui.components.MovieColumn

@Composable
fun BookmarksScreen(
    modifier: Modifier = Modifier,
    onNavigateToDetailMovie: (Int) -> Unit,
) {
    val viewModel: BookmarksScreenViewModel = hiltViewModel()
    val favMovies by viewModel.favMovies.collectAsState()

    LazyColumn(
        modifier = modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(bottom = 8.dp)
    ) {
        item {
            Text(
                text = "Favorite Movies",
                fontSize = 24.sp,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onPrimary,
                modifier = modifier
                    .fillMaxWidth()
                    .padding(bottom  =  16.dp)
            )
        }

        if (favMovies.isEmpty()) {
            item {
                Column(
                    modifier = modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.dark_empty_state),
                        contentDescription = null,
                    )
                }

            }
        } else {
            items(favMovies.size) { index ->
                MovieColumn(
                    movies = favMovies[index],
                    modifier = modifier,
                    onClick = { onNavigateToDetailMovie(favMovies[index].id) }
                )
            }
        }

    }
}