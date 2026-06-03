package com.test.movieapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.test.movieapp.presentation.detail.DetailScreen
import com.test.movieapp.presentation.genre.GenreScreen
import com.test.movieapp.presentation.movies.MoviesScreen
import com.test.movieapp.presentation.navigation.Screen
import com.test.movieapp.ui.theme.MovieAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MovieAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MovieAppNavigation()
                }
            }
        }
    }
}

@Composable
fun MovieAppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.Genre.route
    ) {
        composable(Screen.Genre.route) {
            GenreScreen(
                onGenreClick = { genre ->
                    navController.navigate(
                        Screen.Movies.createRoute(genre.id, genre.name)
                    )
                }
            )
        }

        composable(
            route = Screen.Movies.route,
            arguments = listOf(
                navArgument("genreId") { type = NavType.StringType },
                navArgument("genreName") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val genreId = backStackEntry.arguments?.getString("genreId") ?: ""
            val genreName = backStackEntry.arguments?.getString("genreName") ?: ""
            MoviesScreen(
                genreId = genreId,
                genreName = genreName,
                onMovieClick = { movie ->
                    navController.navigate(Screen.Detail.createRoute(movie.id))
                },
                onBackClick = { navController.popBackStack() }
            )
        }

        composable(
            route = Screen.Detail.route,
            arguments = listOf(
                navArgument("movieId") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val movieId = backStackEntry.arguments?.getInt("movieId") ?: 0
            DetailScreen(
                movieId = movieId,
                onBackClick = { navController.popBackStack() }
            )
        }
    }
}
