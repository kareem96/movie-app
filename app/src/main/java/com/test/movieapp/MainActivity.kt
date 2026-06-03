package com.test.movieapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.core.tween
import com.test.movieapp.presentation.detail.DetailScreen
import com.test.movieapp.presentation.genre.GenreScreen
import com.test.movieapp.presentation.movies.MoviesScreen
import com.test.movieapp.presentation.navigation.Screen
import com.test.movieapp.presentation.theme.ThemeViewModel
import com.test.movieapp.ui.theme.MovieAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val themeViewModel: ThemeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val isDarkTheme by themeViewModel.isDarkTheme.collectAsStateWithLifecycle()
            MovieAppTheme(darkTheme = isDarkTheme) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MovieAppNavigation(
                        onThemeToggle = { themeViewModel.toggleTheme() },
                        isDarkTheme = isDarkTheme
                    )
                }
            }
        }
    }
}

@Composable
fun MovieAppNavigation(
    onThemeToggle: () -> Unit,
    isDarkTheme: Boolean
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.Genre.route,
        enterTransition = {
            slideInHorizontally(
                initialOffsetX = { it },
                animationSpec = tween(300)
            ) + fadeIn(animationSpec = tween(300))
        },
        exitTransition = {
            slideOutHorizontally(
                targetOffsetX = { -it / 3 },
                animationSpec = tween(300)
            ) + fadeOut(animationSpec = tween(300))
        },
        popEnterTransition = {
            slideInHorizontally(
                initialOffsetX = { -it / 3 },
                animationSpec = tween(300)
            ) + fadeIn(animationSpec = tween(300))
        },
        popExitTransition = {
            slideOutHorizontally(
                targetOffsetX = { it },
                animationSpec = tween(300)
            ) + fadeOut(animationSpec = tween(300))
        }
    ) {
        composable(Screen.Genre.route) {
            GenreScreen(
                onGenreClick = { genre ->
                    navController.navigate(
                        Screen.Movies.createRoute(genre.id, genre.name)
                    )
                },
                onThemeToggle = onThemeToggle,
                isDarkTheme = isDarkTheme
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
                onBackClick = { navController.popBackStack() },
                onThemeToggle = onThemeToggle,
                isDarkTheme = isDarkTheme
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
                onBackClick = { navController.popBackStack() },
                onThemeToggle = onThemeToggle,
                isDarkTheme = isDarkTheme
            )
        }
    }
}
