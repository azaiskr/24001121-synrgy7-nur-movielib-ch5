package com.synrgy.mobielib.ui.main.profile

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.work.WorkManager
import coil.compose.rememberAsyncImagePainter
import com.synrgy.common.IMAGE_MANIPULATION_WORK_NAME
import com.synrgy.common.KEY_IMAGE_URI
import com.synrgy.common.Resource
import com.synrgy.domain.model.User
import com.synrgy.mobielib.R
import com.synrgy.mobielib.ui.components.MainButton
import com.synrgy.mobielib.ui.components.UpdateProfileDialog

@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    user: User,
    viewModel: ProfileViewModel = hiltViewModel(),
) {
    val context = LocalContext.current
    val userData by viewModel.user.observeAsState()
    val updateDataResponse by viewModel.updateDataResponse.collectAsState()
    val dataProfile = listOf(
        "Username" to userData?.username,
        "Email" to userData?.email,
        "Full name" to userData?.name,
        "Phone" to userData?.phone,
        "Date of birth" to userData?.dob,
        "Address" to userData?.address
    )
    var permissionGranted by remember { mutableStateOf(false) }
    var showLogOutDialog by remember { mutableStateOf(false) }
    var showUpdateDataDialog by remember { mutableStateOf(false) }
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    //Launcher gallery
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri ->
            if (uri != null) {
                imageUri = uri
            }
        }
    )

    //Recompose when entering profile screen
    LaunchedEffect(Unit) {
        viewModel.getUser(user.email, user.password)
    }

    //Launcher notification permission
    val notificationPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted: Boolean ->
            permissionGranted = isGranted
            if (isGranted && imageUri != null) {
                viewModel.applyBlur(imageUri!!)
            }
            if (!isGranted) {
                Toast.makeText(context, "Notification permission is required", Toast.LENGTH_SHORT)
                    .show()
                return@rememberLauncherForActivityResult
            }

        }
    )
    LaunchedEffect(imageUri, permissionGranted) {
        imageUri?.let {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                if (ContextCompat.checkSelfPermission(
                        context,
                        Manifest.permission.POST_NOTIFICATIONS
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    notificationPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                } else {
                    viewModel.applyBlur(imageUri!!)
                }
            } else {
                viewModel.applyBlur(imageUri!!)
            }

        }
    }

    val workManager = WorkManager.getInstance(LocalContext.current)
    val workInfo by workManager.getWorkInfosForUniqueWorkLiveData(IMAGE_MANIPULATION_WORK_NAME)
        .observeAsState()
    LaunchedEffect(workInfo) {
        workInfo?.firstOrNull()?.let {
            if (it.state.isFinished) {
                val outputImageUri = it.outputData.getString(KEY_IMAGE_URI)
                Log.d("ProfileScreen", "OutputImageUri: $outputImageUri")
                if (outputImageUri != null) {
                    viewModel.updateProfileImage(outputImageUri, user.email)
                    Log.d("ProfileScreen", "update Uri : $outputImageUri")
                    viewModel.getUser(user.email, user.password)
                }
            }
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Box(
            contentAlignment = Alignment.BottomEnd,
            modifier = modifier
                .size(96.dp)
        ) {
            userData?.profileImg?.let { uri ->
                Image(
                    painter = rememberAsyncImagePainter(model = uri),
                    modifier = modifier
                        .size(96.dp)
                        .clip(CircleShape)
                        .background(Color.White),
                    contentScale = ContentScale.Crop,
                    contentDescription = null,
                )

            } ?: run {
                Image(
                    painter = painterResource(id = R.drawable.profile),
                    modifier = modifier
                        .size(96.dp)
                        .clip(CircleShape)
                        .background(Color.Gray),
                    contentScale = ContentScale.Crop,
                    contentDescription = null
                )
            }
            IconButton(
                onClick = {
                    launcher.launch("image/*")
                },
                modifier = modifier
                    .size(32.dp)
                    .background(Color.Black, CircleShape)
                    .padding(4.dp),
                colors = IconButtonDefaults.iconButtonColors(
                    containerColor = Color.White
                )
            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Edit Profile Picture",
                    tint = Color.Black,
                    modifier = modifier.padding(4.dp)
                )
            }
        }
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
            onClick = { showUpdateDataDialog = true },
            outlined = true,
            labelText = "Update data"
        )
        Spacer(modifier = Modifier.height(8.dp))
        MainButton(
            modifier = modifier,
            onClick = {
                showLogOutDialog = true
            },
            outlined = false,
            labelText = "Logout"
        )
    }

    if (showUpdateDataDialog) {
        UpdateProfileDialog(
            title = "Update Profile",
            oldData = userData!!,
            onDismissRequest = {
                showUpdateDataDialog = false
            },
            onConfirm = { name, phone, dob, address ->
                viewModel.updateUserData(name, phone, dob, address, user.email)
//                when(updateDataResponse){
//                    is Resource.Success -> {
//                        showUpdateDataDialog = false
//                        Log.d("ProfileScreen", "onConfirm: ${(updateDataResponse as Resource.Success<Unit>).data}")
//                    }
//                    is Resource.Error -> Toast.makeText(context, (updateDataResponse as Resource.Error).exception, Toast.LENGTH_SHORT).show()
//                    else -> {}
//                }
                showUpdateDataDialog = false
                viewModel.getUser(user.email, user.password)
            },
        )
        Box(
            modifier = modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.8f))
        )
    }
    if (showLogOutDialog) {
        ConfirmDialog(
            title = "Log out?",
            msg = "You'll be logged out of your account.",
            onConfirm = {
                viewModel.clearSession()
                showLogOutDialog = false
            },
            onDismiss = {
                showLogOutDialog = false
            },
            confirmLabel = "Log out",
            dismissLabel = "Cancel",
        )
        Box(
            modifier = modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.8f))
        )

    }
}

@Composable
fun ConfirmDialog(
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
    confirmLabel: String,
    dismissLabel: String,
    title: String,
    msg: String,
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = title,
                color = Color.Black,
                fontWeight = FontWeight.Bold
            )
        },
        text = {
            Text(
                text = msg,
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Black
            )
        },
        confirmButton = {
            Button(
                onClick = onConfirm,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Black,
                    contentColor = Color.White
                )
            ) {
                Text(confirmLabel)
            }
        },
        dismissButton = {
            Button(
                onClick = onDismiss,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent,
                    contentColor = Color.Black
                )
            ) {
                Text(dismissLabel, fontWeight = FontWeight.Bold)
            }
        },
        containerColor = Color.LightGray,
        tonalElevation = 8.dp
    )
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
