package com.synrgy.mobielib.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.synrgy.mobielib.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBar(
    modifier: Modifier = Modifier,
    sectionTitle: String = "",
    contentColor: Color = Color.White,
    backIcon: Boolean = false,
    onBackClick: () -> Unit,
    onProfileClick: () -> Unit,
    profileIcon: Boolean = true,
    profileImage: String?,
) {

    androidx.compose.material3.TopAppBar(
        title = {
            Text(
                text = sectionTitle,
                color = contentColor,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        },
        navigationIcon = {
            if (backIcon) {
                IconButton(
                    onClick = { onBackClick() },
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.round_arrow_back),
                        contentDescription = "back",
                        tint = contentColor
                    )
                }
            }
        },
        actions = {
            if (profileIcon) {
                ProfileIcon(
                    modifier = modifier,
                    onClick = onProfileClick,
                    profileImage = profileImage,
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Transparent,
            navigationIconContentColor = contentColor,
            titleContentColor = contentColor
        )
    )
}


@Composable
fun ProfileIcon(
    modifier: Modifier,
    onClick: () -> Unit,
    profileImage: String?
) {
    profileImage?.let {
        Image(
            painter = rememberAsyncImagePainter(model = it),
            contentDescription = null,
            modifier = modifier
                .padding(end = 8.dp)
                .clip(CircleShape)
                .size(32.dp)
                .background(Color.White)
                .clickable { onClick() },
            contentScale = ContentScale.Crop
        )
    } ?: run {
        Image(
            painter = painterResource(id = R.drawable.profile),
            contentDescription = null,
            modifier = modifier
                .padding(end = 8.dp)
                .clip(CircleShape)
                .size(32.dp)
                .clickable { onClick() },
            contentScale = ContentScale.Crop
        )
    }

}