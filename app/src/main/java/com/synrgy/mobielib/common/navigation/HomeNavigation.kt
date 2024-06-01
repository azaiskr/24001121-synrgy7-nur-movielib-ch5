package com.synrgy.mobielib.common.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.synrgy.mobielib.data.local.UserModel
import com.synrgy.mobielib.presentation.ui.components.TopAppBar
import com.synrgy.mobielib.presentation.ui.main.detailMovie.DetailMovieScreen
import com.synrgy.mobielib.presentation.ui.main.home.ListMovieScreen
import com.synrgy.mobielib.presentation.ui.main.profile.ProfileScreen

@Composable
fun HomeNavigation(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    user: UserModel,
    onLogOut: () -> Unit,
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        containerColor = Color.Black,
        topBar = {
            TopAppBar(
                onBackClick = { navController.popBackStack() },
                onProfileClick = { navController.navigate(Screen.Profile.route) },
                sectionTitle = when (currentRoute) {
                    Screen.Profile.route -> "Profile"
                    Screen.DetailMovie.route -> "Detail Movie"
                    Screen.Home.route -> "Hi, ${user.username}"
                    else -> ""
                },
                backIcon = currentRoute != Screen.Home.route,
                profileIcon = currentRoute != Screen.Profile.route
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
                ProfileScreen(user = user)
            }
        }
    }
}