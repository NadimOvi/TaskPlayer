package com.taskplayer.features.feed

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.taskplayer.core.theme.*
import com.taskplayer.features.feed.components.*

@Composable
fun FeedScreen(
    viewModel: FeedViewModel,
    onViewProfile: (String) -> Unit,
    onSavedClick: () -> Unit
) {
    val uiState  by viewModel.uiState.collectAsState()
    val listState = rememberLazyListState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
    ) {
        if (uiState.isLoading) {
            LazyColumn(
                contentPadding      = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                item { AppHeader(onSavedClick = onSavedClick) }
                items(5) { ShimmerCard() }
            }
        } else {
            LazyColumn(
                state               = listState,
                contentPadding      = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                item(key = "header") {
                    AppHeader(onSavedClick = onSavedClick)
                }

                uiState.error?.let { error ->
                    item(key = "error") {
                        Surface(
                            modifier = Modifier.fillMaxWidth(),
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
                }

                items(
                    items = uiState.videos,
                    key   = { it.id }
                ) { video ->
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

    uiState.smartSheetVideoId?.let { id ->
        uiState.videos.find { it.id == id }?.let { video ->
            SmartSummarySheet(
                video     = video,
                onDismiss = { viewModel.closeSmartSheet() }
            )
        }
    }
}

@Composable
fun AppHeader(onSavedClick: () -> Unit) {
    Row(
        modifier              = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment     = Alignment.CenterVertically
    ) {
        Text(
            text  = "TaskPlayer",
            style = MaterialTheme.typography.headlineLarge,
            color = TextPrimary
        )
        IconButton(onClick = onSavedClick) {
            Icon(
                imageVector        = Icons.Default.Bookmark,
                contentDescription = "Saved",
                tint               = AccentCyan,
                modifier           = Modifier.size(26.dp)
            )
        }
    }
}