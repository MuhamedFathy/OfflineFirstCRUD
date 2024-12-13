package com.github.offlinefirstcrud.presentation.composable.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.github.offlinefirstcrud.presentation.composable.PostDetailsScreen
import com.github.offlinefirstcrud.presentation.composable.PostsScreen

@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = NavRoutes.PostsScreen.route
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination
    ) {
        composable(route = NavRoutes.PostsScreen.route) {
            PostsScreen(
                itemCallback = { navController.navigate(NavRoutes.PostDetailsScreen.route) }
            )
        }
        composable(route = NavRoutes.PostDetailsScreen.route) {
            PostDetailsScreen()
        }
    }
}
