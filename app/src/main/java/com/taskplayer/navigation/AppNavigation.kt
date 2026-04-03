package com.taskplayer.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import coil.ImageLoader
import coil.disk.DiskCache
import coil.memory.MemoryCache
import com.taskplayer.data.repository.VideoRepository
import com.taskplayer.features.feed.FeedScreen
import com.taskplayer.features.feed.FeedViewModel
import com.taskplayer.features.profile.ExpertProfileScreen
import com.taskplayer.features.profile.ExpertProfileViewModel
import com.taskplayer.features.saved.SavedVideosScreen
import com.taskplayer.features.saved.SavedViewModel

sealed class Screen(val route: String) {
    object Feed    : Screen("feed")
    object Saved   : Screen("saved")
    object Profile : Screen("profile/{expertId}") {
        fun createRoute(expertId: String) = "profile/$expertId"
    }
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val context       = LocalContext.current
    val repository    = remember { VideoRepository() }

    val imageLoader = remember {
        ImageLoader.Builder(context)
            .memoryCache {
                MemoryCache.Builder(context)
                    .maxSizePercent(0.25)
                    .build()
            }
            .diskCache {
                DiskCache.Builder()
                    .directory(context.cacheDir.resolve("image_cache"))
                    .maxSizePercent(0.02)
                    .build()
            }
            .build()
    }

    val feedVm  = remember {
        FeedViewModel(
            repository  = repository,
            imageLoader = imageLoader,
            context     = context
        )
    }
    val savedVm = remember { SavedViewModel(repository) }

    NavHost(navController = navController, startDestination = Screen.Feed.route) {

        composable(Screen.Feed.route) {
            FeedScreen(
                viewModel     = feedVm,
                onViewProfile = { navController.navigate(Screen.Profile.createRoute(it)) },
                onSavedClick  = { navController.navigate(Screen.Saved.route) }
            )
        }

        composable(Screen.Saved.route) {
            SavedVideosScreen(
                viewModel     = savedVm,
                onBack        = { navController.popBackStack() },
                onViewProfile = { navController.navigate(Screen.Profile.createRoute(it)) }
            )
        }

        composable(
            route     = Screen.Profile.route,
            arguments = listOf(navArgument("expertId") { type = NavType.StringType })
        ) { back ->
            val expertId = back.arguments?.getString("expertId") ?: return@composable
            val vm       = remember { ExpertProfileViewModel(repository, expertId) }
            ExpertProfileScreen(viewModel = vm, onBack = { navController.popBackStack() })
        }
    }
}