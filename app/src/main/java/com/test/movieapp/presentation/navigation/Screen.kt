package com.test.movieapp.presentation.navigation

import android.net.Uri

sealed class Screen(val route: String) {
    object Genre : Screen("genre")
    object Movies : Screen("movies/{genreId}/{genreName}") {
        fun createRoute(genreId: Int, genreName: String): String =
            "movies/$genreId/${Uri.encode(genreName)}"
    }
    object Detail : Screen("detail/{movieId}") {
        fun createRoute(movieId: Int): String = "detail/$movieId"
    }
}
