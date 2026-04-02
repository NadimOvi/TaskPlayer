package com.taskplayer.features.feed

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.taskplayer.core.theme.*
import com.taskplayer.features.feed.components.ShimmerCard
import com.taskplayer.features.feed.components.SmartSummarySheet
import com.taskplayer.features.feed.components.VideoCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FeedScreen(
    viewModel: FeedViewModel,
    onViewProfile: (String) -> Unit,
    onSavedClick: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text  = "TaskPlayer",
                        style = MaterialTheme.typography.headlineMedium,
                        color = PrimaryGold
                    )
                },
                actions = {
                    IconButton(onClick = onSavedClick) {
                        Icon(
                            imageVector = Icons.Default.BookmarkBorder,
                            contentDescription = "Saved",
                            tint = TextSecondary
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = BackgroundDark
                )
            )
        },
        containerColor = BackgroundDark
    ) { padding ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            if (uiState.isLoading) {
                LazyColumn(
                    contentPadding        = PaddingValues(16.dp),
                    verticalArrangement   = Arrangement.spacedBy(16.dp)
                ) {
                    items(4) { ShimmerCard() }
                }
            } else {
                LazyColumn(
                    contentPadding      = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(uiState.videos, key = { it.id }) { video ->
                        VideoCard(
                            video         = video,
                            onLike        = { viewModel.toggleLike(video.id) },
                            onSave        = { viewModel.toggleSave(video.id) },
                            onUnlock      = { viewModel.unlockVideo(video.id) },
                            onViewProfile = { onViewProfile(video.expert.id) },
                            onSmart       = { viewModel.openSmartSheet(video.id) },
                            onFollow      = { viewModel.toggleFollow(video.expert.id) }
                        )
                    }
                }
            }
        }
    }

    // Smart Summary Bottom Sheet
    uiState.smartSheetVideoId?.let { videoId ->
        val video = uiState.videos.find { it.id == videoId }
        video?.let {
            SmartSummarySheet(
                video   = it,
                onDismiss = { viewModel.closeSmartSheet() }
            )
        }
    }
}