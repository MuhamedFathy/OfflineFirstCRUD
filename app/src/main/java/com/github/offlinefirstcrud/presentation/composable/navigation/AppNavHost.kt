package com.github.offlinefirstcrud.presentation.composable.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.github.offlinefirstcrud.presentation.composable.CreateUpdatePostScreen
import com.github.offlinefirstcrud.presentation.composable.PostDetailsScreen
import com.github.offlinefirstcrud.presentation.composable.PostsScreen
import com.github.offlinefirstcrud.presentation.viewmodel.uimodel.PostUiModel

@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = NavRoutes.PostsScreen.route
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination,
        enterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Start) },
        exitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Start) },
        popEnterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.End) },
        popExitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.End) }
    ) {
        composable(route = NavRoutes.PostsScreen.route) {
            PostsScreen(
                itemCallback = { navController.navigate("${NavRoutes.PostDetailsScreen.route}/${it.title}/${it.body}") },
                editCallback = { navController.navigate("${NavRoutes.CreatePostScreen.route}/${it.id}/${it.title}/${it.body}") }
            )
        }

        composable(
            route = "${NavRoutes.PostDetailsScreen.route}/{title}/{body}",
            arguments = listOf(
                navArgument("title") { type = NavType.StringType },
                navArgument("body") { type = NavType.StringType }
            )
        ) {
            val title = it.arguments?.getString("title")
            val body = it.arguments?.getString("body")

            PostDetailsScreen(title.orEmpty(), body.orEmpty())
        }

        composable(
            route = "${NavRoutes.CreatePostScreen.route}/{id}/{title}/{body}",
            arguments = listOf(
                navArgument("id") { type = NavType.IntType },
                navArgument("title") {
                    type = NavType.StringType
                    nullable = true
                },
                navArgument("body") {
                    type = NavType.StringType
                    nullable = true
                }
            )
        ) {
            val id = it.arguments?.getInt("id") ?: -1
            val title = it.arguments?.getString("title")
            val body = it.arguments?.getString("body")
            CreateUpdatePostScreen(
                postUiModel = if (id != -1 && title != null && body != null) PostUiModel(id, title, body) else null,
                createUpdateAction = { navController.popBackStack() }
            )
        }
    }
}
