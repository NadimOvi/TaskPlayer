package com.taskplayer.features.feed

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.taskplayer.core.theme.*
import com.taskplayer.features.feed.components.*

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
                        color = TextPrimary
                    )
                },
                actions = {
                    IconButton(onClick = onSavedClick) {
                        Icon(
                            imageVector        = Icons.Default.Bookmark,
                            contentDescription = "Saved",
                            tint               = AccentCyan
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

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            uiState.error?.let { error ->
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 4.dp),
                    color    = AccentRed.copy(alpha = 0.15f),
                    shape    = MaterialTheme.shapes.medium
                ) {
                    Text(
                        text     = error,
                        color    = AccentRed,
                        style    = MaterialTheme.typography.labelMedium,
                        modifier = Modifier.padding(12.dp)
                    )
                }
            }

            if (uiState.isLoading) {
                LazyColumn(
                    contentPadding      = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(5) { ShimmerCard() }
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

    uiState.smartSheetVideoId?.let { id ->
        uiState.videos.find { it.id == id }?.let { video ->
            SmartSummarySheet(
                video     = video,
                onDismiss = { viewModel.closeSmartSheet() }
            )
        }
    }
}