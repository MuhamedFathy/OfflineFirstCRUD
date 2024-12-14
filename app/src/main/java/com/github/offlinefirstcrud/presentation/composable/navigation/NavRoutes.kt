package com.github.offlinefirstcrud.presentation.composable.navigation

import com.github.offlinefirstcrud.R

sealed class NavRoutes(val route: String) {

    data object PostsScreen : NavRoutes(route = "posts_screen")
    data object PostDetailsScreen : NavRoutes(route = "post_details_screen")
    data object CreatePostScreen : NavRoutes(route = "create_post_screen")
}

fun getScreenTitle(route: String?): Int? = when (route) {
    NavRoutes.PostsScreen.route -> R.string.posts_screen_title
    "${NavRoutes.PostDetailsScreen.route}/{title}/{body}" -> R.string.post_details_title
    "${NavRoutes.CreatePostScreen.route}/{id}/{title}/{body}" -> R.string.create_post_title
    else -> null
}
