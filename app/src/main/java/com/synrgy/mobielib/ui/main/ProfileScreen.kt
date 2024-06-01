package com.synrgy.mobielib.ui.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.synrgy.mobielib.R
import com.synrgy.mobielib.ui.components.MainButton

@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxSize().padding(horizontal = 16.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.profile),
            modifier = modifier
                .size(96.dp)
                .clip(CircleShape)
                .background(Color.Gray),
            contentScale = ContentScale.Crop,
            contentDescription = null
        )
        Spacer(modifier = Modifier.height(24.dp))
        Text(
            text = "N Azis Kurnia R",
            modifier = modifier
                .padding(vertical = 16.dp),
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            color = Color.White
        )
        Spacer(modifier = Modifier.height(32.dp))
        MainButton(modifier = modifier, onClick = { /*TODO*/ }, outlined = true, labelText = "Update data")
        Spacer(modifier = Modifier.height(8.dp))
        MainButton(modifier = modifier, onClick = { /*TODO*/ }, outlined = false, labelText = "Logout")
    }
}