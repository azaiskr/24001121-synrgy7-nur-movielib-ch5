package com.synrgy.mobielib.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.unit.dp

@Composable
fun LoadingScreen(){
    Box (
        modifier = Modifier
            .height(160.dp)
            .fillMaxWidth(),
        contentAlignment = Alignment.Center
    ){
        CircularProgressIndicator(
            color = Color.White,
            strokeWidth = 4.dp,
            modifier = Modifier.size(32.dp),
            trackColor = Color.Black,
            strokeCap = StrokeCap.Round
        )
    }
}