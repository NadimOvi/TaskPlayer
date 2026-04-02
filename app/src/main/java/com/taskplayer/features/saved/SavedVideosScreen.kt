package com.taskplayer.features.saved

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.taskplayer.core.theme.*
import com.taskplayer.features.profile.ProfileVideoItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SavedVideosScreen(
    viewModel: SavedViewModel,
    onBack: () -> Unit,
    onViewProfile: (String) -> Unit
) {
    val savedVideos by viewModel.savedVideos.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text  = "Saved Videos",
                        style = MaterialTheme.typography.titleLarge,
                        color = TextPrimary
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = TextPrimary
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

        if (savedVideos.isEmpty()) {
            Box(
                modifier         = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        imageVector        = Icons.Default.BookmarkBorder,
                        contentDescription = null,
                        tint               = TextHint,
                        modifier           = Modifier.size(48.dp)
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text  = "No saved videos yet",
                        style = MaterialTheme.typography.titleMedium,
                        color = TextHint
                    )
                    Text(
                        text  = "Tap the bookmark icon on any video to save it",
                        style = MaterialTheme.typography.bodyMedium,
                        color = TextHint
                    )
                }
            }
        } else {
            LazyColumn(
                modifier        = Modifier.padding(padding),
                contentPadding  = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(savedVideos, key = { it.id }) { video ->
                    ProfileVideoItem(video = video)
                }
            }
        }
    }
}