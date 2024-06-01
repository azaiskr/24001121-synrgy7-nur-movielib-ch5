package com.synrgy.mobielib.common.navigation

sealed class Screen (
    var route: String,
) {
    data object Home : Screen("home")
    data object DetailMovie: Screen("home/{movieId}"){
        fun createRoute(movieId: Int) = "home/$movieId"
    }
    data object Profile : Screen("home/profile")
}

