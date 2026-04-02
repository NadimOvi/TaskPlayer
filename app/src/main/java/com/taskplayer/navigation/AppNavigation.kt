package com.taskplayer.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
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
    val navController  = rememberNavController()
    val repository     = remember { VideoRepository() }

    val feedViewModel    = remember { FeedViewModel(repository) }
    val savedViewModel   = remember { SavedViewModel(repository) }

    NavHost(
        navController    = navController,
        startDestination = Screen.Feed.route
    ) {
        composable(Screen.Feed.route) {
            FeedScreen(
                viewModel     = feedViewModel,
                onViewProfile = { expertId ->
                    navController.navigate(Screen.Profile.createRoute(expertId))
                },
                onSavedClick  = {
                    navController.navigate(Screen.Saved.route)
                }
            )
        }

        composable(Screen.Saved.route) {
            SavedVideosScreen(
                viewModel     = savedViewModel,
                onBack        = { navController.popBackStack() },
                onViewProfile = { expertId ->
                    navController.navigate(Screen.Profile.createRoute(expertId))
                }
            )
        }

        composable(
            route     = Screen.Profile.route,
            arguments = listOf(navArgument("expertId") { type = NavType.StringType })
        ) { backStackEntry ->
            val expertId  = backStackEntry.arguments?.getString("expertId") ?: return@composable
            val profileVm = remember { ExpertProfileViewModel(repository, expertId) }
            ExpertProfileScreen(
                viewModel = profileVm,
                onBack    = { navController.popBackStack() }
            )
        }
    }
}