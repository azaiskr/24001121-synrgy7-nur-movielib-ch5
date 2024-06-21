package com.synrgy.mobielib.utils

import android.util.Log
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.synrgy.common.Resource
import com.synrgy.common.Screen
import com.synrgy.domain.model.User
import com.synrgy.mobielib.ui.auth.AuthViewModel
import com.synrgy.mobielib.ui.components.TopAppBar
import com.synrgy.mobielib.ui.main.detailMovie.DetailMovieScreen
import com.synrgy.mobielib.ui.main.home.ListMovieScreen
import com.synrgy.mobielib.ui.main.profile.ProfileScreen

@Composable
fun HomeNavigation(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    user: User,
    onLogOut: () -> Unit,
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val viewModel: AuthViewModel = hiltViewModel()
    val userData by viewModel.user.observeAsState()

    LaunchedEffect(currentRoute) {
            viewModel.getUser(user.email, user.password)
    }

    LaunchedEffect(userData) {
        userData?.let {
            Log.d("Home Navigation", "Observe: ${it.profileImg}")
        }
    }

    Scaffold(
        containerColor = Color.Black,
        topBar = {
            TopAppBar(
                onBackClick = { navController.popBackStack() },
                onProfileClick = { navController.navigate(Screen.Profile.route) },
                sectionTitle = when (currentRoute) {
                    Screen.Profile.route -> "Profile"
                    Screen.DetailMovie.route -> "Detail Movie"
                    Screen.Home.route -> "Hi, ${userData?.username}"
                    else -> ""
                },
                backIcon = currentRoute != Screen.Home.route,
                profileIcon = currentRoute != Screen.Profile.route,
                profileImage = userData?.profileImg,
            )
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = modifier.padding(innerPadding),
        ) {

            composable(Screen.Home.route) {
                ListMovieScreen(
                    onNavigateToDetail = { navController.navigate(Screen.DetailMovie.createRoute(it)) }
                )
            }
            composable(
                route = Screen.DetailMovie.route,
                arguments = listOf(navArgument("movieId") { type = NavType.IntType })
            ) {
                val movieId = it.arguments?.getInt("movieId") ?: 0
                DetailMovieScreen(movieId = movieId)
            }
            composable(Screen.Profile.route) {
                ProfileScreen(user = userData!!)
            }
        }
    }
}