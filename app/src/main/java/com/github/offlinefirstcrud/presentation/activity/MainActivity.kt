package com.github.offlinefirstcrud.presentation.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.github.offlinefirstcrud.presentation.composable.navigation.AppNavHost
import com.github.offlinefirstcrud.presentation.composable.navigation.NavRoutes
import com.github.offlinefirstcrud.presentation.composable.navigation.getScreenTitle
import com.github.offlinefirstcrud.presentation.theme.OfflineFirstCRUDTheme

@OptIn(ExperimentalMaterial3Api::class)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            val backStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = backStackEntry?.destination?.route
            var itemsCount by remember { mutableIntStateOf(2) }

            OfflineFirstCRUDTheme {
                Scaffold(
                    topBar = {
                        CenterAlignedTopAppBar(
                            title = {
                                val title = getScreenTitle(currentRoute)
                                Text(
                                    text = if (title == null) "" else stringResource(title),
                                    style = MaterialTheme.typography.titleLarge
                                )
                            },
                            navigationIcon = {
                                if (currentRoute != NavRoutes.PostsScreen.route) {
                                    IconButton(onClick = { navController.popBackStack() }) {
                                        Icon(
                                            Icons.AutoMirrored.Filled.ArrowBack,
                                            contentDescription = "Back"
                                        )
                                    }
                                }
                            }
                        )
                    },
                    floatingActionButton = {
                        if (currentRoute == NavRoutes.PostsScreen.route) {
                            FloatingActionButton(
                                onClick = { itemsCount += 1 }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Add,
                                    contentDescription = "Add Post"
                                )
                            }
                        }
                    }
                ) { contentPadding ->
                    AppNavHost(
                        modifier = Modifier.padding(contentPadding),
                        navController = navController,
                        itemsCount = itemsCount
                    )
                }
            }
        }
    }
}

