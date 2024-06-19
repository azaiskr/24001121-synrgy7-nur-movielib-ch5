package com.synrgy.mobielib.ui.main.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.synrgy.mobielib.ui.components.MainButton
import com.synrgy.mobielib.ui.components.UpdateProfileDialog
import com.synrgy.domain.model.User
import com.synrgy.mobielib.R

@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    user: User,
    viewModel: ProfileViewModel = hiltViewModel(),
) {
    val dataProfile = listOf(
        "Username" to user.username,
        "Email" to user.email,
        "Full name" to user.name,
        "Phone" to user.phone,
        "Date of birth" to user.dob,
        "Address" to user.address
    )

    var showDialog by rememberSaveable { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.profile),
            modifier = modifier
                .size(96.dp)
                .clip(CircleShape)
                .background(Color.Gray)
                .align(Alignment.CenterHorizontally),
            contentScale = ContentScale.Crop,
            contentDescription = null
        )
        Spacer(modifier = Modifier.height(24.dp))
        Card(
            modifier = modifier
                .fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.1f))
        ) {
            Column(
                modifier = modifier
                    .padding(16.dp)
            ) {
                dataProfile.forEach {
                    DisplayData(label = it.first, data = it.second)
                    HorizontalDivider(
                        thickness = 1.dp,
                        color = Color.White.copy(alpha = 0.5f)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
        Spacer(modifier = Modifier.height(32.dp))
        MainButton(
            modifier = modifier,
            onClick = { showDialog = true },
            outlined = true,
            labelText = "Update data"
        )
        Spacer(modifier = Modifier.height(8.dp))
        MainButton(
            modifier = modifier,
            onClick = { viewModel.clearSession() },
            outlined = false,
            labelText = "Logout"
        )

        if (showDialog) {
            UpdateProfileDialog(
                onDismissRequest = { showDialog = false },
                onConfirm = {
                    //todo : update data
                    showDialog = false
                }
            )
        }
    }
}

@Composable
fun DisplayData(label: String, data: String?) {
    Row(
        modifier = Modifier.padding(vertical = 8.dp),
        verticalAlignment =
        Alignment.CenterVertically
    ) {
        Text(
            text = label,
            fontWeight = FontWeight.Light,
            fontSize = 14.sp,
            color = Color.White
        )
        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = data ?: "Tidak ada data",
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            color = Color.White
        )
    }
}
