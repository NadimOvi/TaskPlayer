package com.taskplayer.features.profile

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.taskplayer.core.theme.*
import com.taskplayer.data.model.AccessType
import com.taskplayer.data.model.Video

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpertProfileScreen(
    viewModel: ExpertProfileViewModel,
    onBack: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    val expert  = uiState.expert ?: return

    Scaffold(
        topBar = {
            TopAppBar(
                title          = { Text("Expert Profile", color = TextPrimary) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, null, tint = TextPrimary)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = BackgroundDark)
            )
        },
        containerColor = BackgroundDark
    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        Brush.verticalGradient(
                            listOf(GradientStart.copy(alpha = 0.4f), BackgroundDark)
                        )
                    )
                    .padding(24.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {

                    AsyncImage(
                        model              = expert.photoUrl,
                        contentDescription = expert.name,
                        contentScale       = ContentScale.Crop,
                        modifier           = Modifier
                            .size(100.dp)
                            .clip(CircleShape)
                            .border(
                                3.dp,
                                Brush.sweepGradient(listOf(GradientStart, GradientEnd)),
                                CircleShape
                            )
                    )

                    Spacer(Modifier.height(14.dp))

                    Text(expert.name, style = MaterialTheme.typography.headlineMedium)
                    Text(expert.title, style = MaterialTheme.typography.bodyMedium, color = AccentCyan)

                    Spacer(Modifier.height(18.dp))

                    Row(
                        modifier              = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        StatChip(label = "Followers",  value = "${expert.followers / 1000}K")
                        StatChip(label = "Sessions",   value = "${expert.totalSessions}+")
                        StatChip(label = "Rating",     value = "${expert.rating} ⭐")
                    }

                    Spacer(Modifier.height(18.dp))

                    Row(
                        modifier              = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .clip(RoundedCornerShape(14.dp))
                                .background(
                                    if (expert.isFollowed)
                                        Brush.horizontalGradient(listOf(GradientStart, GradientMid))
                                    else
                                        Brush.horizontalGradient(listOf(CardElevated, CardElevated))
                                )
                                .clickable { viewModel.toggleFollow() }
                                .padding(vertical = 12.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text  = if (expert.isFollowed) "✓ Following" else "+ Follow",
                                style = MaterialTheme.typography.labelLarge,
                                color = if (expert.isFollowed) TextPrimary else TextSecondary
                            )
                        }

                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .clip(RoundedCornerShape(14.dp))
                                .background(Brush.horizontalGradient(listOf(GradientStart, GradientMid)))
                                .clickable { }
                                .padding(vertical = 12.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text  = "Book Session",
                                style = MaterialTheme.typography.labelLarge,
                                color = TextPrimary
                            )
                        }
                    }
                }
            }

            Spacer(Modifier.height(16.dp))

            Column(modifier = Modifier.padding(horizontal = 20.dp)) {

                Text("About", style = MaterialTheme.typography.titleLarge)
                Spacer(Modifier.height(8.dp))
                Text(expert.bio, style = MaterialTheme.typography.bodyLarge)

                Spacer(Modifier.height(24.dp))

                Text(
                    text  = "Videos by ${expert.name.split(" ").first()}",
                    style = MaterialTheme.typography.titleLarge
                )
                Spacer(Modifier.height(12.dp))

                if (uiState.videos.isEmpty()) {
                    Text(
                        text  = "No videos available yet.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = TextHint
                    )
                } else {
                    uiState.videos.forEach { video ->
                        ProfileVideoItem(video)
                        Spacer(Modifier.height(10.dp))
                    }
                }

                Spacer(Modifier.height(24.dp))
            }
        }
    }
}

@Composable
fun StatChip(label: String, value: String) {
    Surface(shape = RoundedCornerShape(12.dp), color = CardDark) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier            = Modifier.padding(horizontal = 20.dp, vertical = 10.dp)
        ) {
            Text(value, style = MaterialTheme.typography.titleMedium, color = TextPrimary)
            Text(label, style = MaterialTheme.typography.labelSmall)
        }
    }
}

@Composable
fun ProfileVideoItem(video: Video) {
    Surface(shape = RoundedCornerShape(14.dp), color = CardDark) {
        Row(
            modifier          = Modifier.fillMaxWidth().padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model              = video.thumbnailUrl,
                contentDescription = null,
                contentScale       = ContentScale.Crop,
                modifier           = Modifier.size(64.dp).clip(RoundedCornerShape(10.dp))
            )
            Spacer(Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text     = video.videoTitle,
                    style    = MaterialTheme.typography.titleMedium,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    text  = video.duration,
                    style = MaterialTheme.typography.labelSmall,
                    color = TextHint
                )
            }
            Spacer(Modifier.width(8.dp))
            Surface(
                shape = RoundedCornerShape(8.dp),
                color = if (video.accessType == AccessType.FREE)
                    AccentGreen.copy(0.15f) else PremiumBadgeBg
            ) {
                Text(
                    text     = if (video.accessType == AccessType.FREE) "Free" else "Premium",
                    style    = MaterialTheme.typography.labelSmall,
                    color    = if (video.accessType == AccessType.FREE) AccentGreen else PremiumGold,
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                )
            }
        }
    }
}