package com.taskplayer.features.profile

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
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
                title  = { Text("Expert Profile", color = TextPrimary) },
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

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
        ) {
            // Header card
            Surface(
                modifier = Modifier.fillMaxWidth(),
                color    = SurfaceDark
            ) {
                Column(
                    modifier            = Modifier.padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    AsyncImage(
                        model              = expert.photoUrl,
                        contentDescription = expert.name,
                        contentScale       = ContentScale.Crop,
                        modifier           = Modifier
                            .size(96.dp)
                            .clip(CircleShape)
                            .border(2.dp, PrimaryGold, CircleShape)
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    Text(
                        text  = expert.name,
                        style = MaterialTheme.typography.headlineMedium,
                        color = TextPrimary
                    )
                    Text(
                        text  = expert.title,
                        style = MaterialTheme.typography.bodyMedium,
                        color = PrimaryGold
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Stats row
                    Row(
                        modifier              = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        StatItem(label = "Followers",
                            value = "${expert.followers / 1000}K")
                        StatItem(label = "Sessions",
                            value = "${expert.totalSessions}+")
                        StatItem(label = "Rating",
                            value = "${expert.rating}",
                            icon  = true)
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Follow + Book row
                    Row(
                        modifier              = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        OutlinedButton(
                            onClick  = { viewModel.toggleFollow() },
                            modifier = Modifier.weight(1f),
                            shape    = RoundedCornerShape(10.dp),
                            border   = BorderStroke(
                                1.dp,
                                if (expert.isFollowed) PrimaryGold else DividerColor
                            )
                        ) {
                            Text(
                                text  = if (expert.isFollowed) "Following" else "Follow",
                                color = if (expert.isFollowed) PrimaryGold else TextSecondary,
                                style = MaterialTheme.typography.labelLarge
                            )
                        }

                        Button(
                            onClick  = { /* Book */ },
                            modifier = Modifier.weight(1f),
                            shape    = RoundedCornerShape(10.dp),
                            colors   = ButtonDefaults.buttonColors(
                                containerColor = PrimaryGold
                            )
                        ) {
                            Text(
                                text  = "Book Session",
                                color = BackgroundDark,
                                style = MaterialTheme.typography.labelLarge
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Bio
            Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                Text(
                    text  = "About",
                    style = MaterialTheme.typography.titleLarge,
                    color = TextPrimary
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text  = expert.bio,
                    style = MaterialTheme.typography.bodyLarge,
                    color = TextSecondary
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Videos by expert
                Text(
                    text  = "Videos by ${expert.name.split(" ").first()}",
                    style = MaterialTheme.typography.titleLarge,
                    color = TextPrimary
                )
                Spacer(modifier = Modifier.height(12.dp))

                uiState.videos.forEach { video ->
                    ProfileVideoItem(video = video)
                    Spacer(modifier = Modifier.height(10.dp))
                }

                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}

@Composable
fun StatItem(label: String, value: String, icon: Boolean = false) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            if (icon) {
                Icon(
                    imageVector        = Icons.Default.Star,
                    contentDescription = null,
                    tint               = PrimaryGold,
                    modifier           = Modifier.size(14.dp)
                )
                Spacer(modifier = Modifier.width(2.dp))
            }
            Text(
                text  = value,
                style = MaterialTheme.typography.titleLarge,
                color = TextPrimary
            )
        }
        Text(
            text  = label,
            style = MaterialTheme.typography.labelSmall,
            color = TextSecondary
        )
    }
}

@Composable
fun ProfileVideoItem(video: Video) {
    Surface(
        shape = RoundedCornerShape(12.dp),
        color = CardDark
    ) {
        Row(
            modifier          = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model              = video.thumbnailUrl,
                contentDescription = video.videoTitle,
                contentScale       = ContentScale.Crop,
                modifier           = Modifier
                    .size(64.dp)
                    .clip(RoundedCornerShape(8.dp))
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text     = video.videoTitle,
                    style    = MaterialTheme.typography.titleMedium,
                    color    = TextPrimary,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text  = video.duration,
                    style = MaterialTheme.typography.labelSmall,
                    color = TextHint
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            Surface(
                shape = RoundedCornerShape(6.dp),
                color = if (video.accessType == AccessType.FREE)
                    AccentGreen.copy(alpha = 0.2f)
                else PremiumBadgeBg
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