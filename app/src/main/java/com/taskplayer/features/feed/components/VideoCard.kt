package com.taskplayer.features.feed.components

import android.view.ViewGroup
import androidx.compose.animation.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import coil.compose.AsyncImage
import com.taskplayer.core.theme.*
import com.taskplayer.data.model.AccessType
import com.taskplayer.data.model.Video

@Composable
fun VideoCard(
    video: Video,
    onLike: () -> Unit,
    onSave: () -> Unit,
    onUnlock: () -> Unit,
    onViewProfile: () -> Unit,
    onSmart: () -> Unit,
    onFollow: () -> Unit
) {
    var isPlaying by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape    = RoundedCornerShape(16.dp),
        colors   = CardDefaults.cardColors(containerColor = CardDark),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column {

            // ── Video / Player area ──────────────────────────────────────
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(210.dp)
            ) {
                val canPlay = video.accessType == AccessType.FREE || video.isUnlocked

                if (isPlaying && canPlay) {
                    VideoPlayer(videoUrl = video.videoUrl)
                } else {
                    // Thumbnail
                    AsyncImage(
                        model              = video.thumbnailUrl,
                        contentDescription = video.videoTitle,
                        contentScale       = ContentScale.Crop,
                        modifier           = Modifier.fillMaxSize()
                    )

                    // Gradient overlay
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                Brush.verticalGradient(
                                    colors = listOf(Color.Transparent, Color(0xCC000000)),
                                    startY = 80f
                                )
                            )
                    )

                    // Duration badge
                    Surface(
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .padding(8.dp),
                        shape  = RoundedCornerShape(6.dp),
                        color  = Color(0xCC000000)
                    ) {
                        Text(
                            text     = video.duration,
                            style    = MaterialTheme.typography.labelSmall,
                            color    = TextPrimary,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 3.dp)
                        )
                    }

                    // Play button
                    if (canPlay) {
                        IconButton(
                            onClick  = { isPlaying = true },
                            modifier = Modifier
                                .align(Alignment.Center)
                                .size(52.dp)
                                .background(PrimaryGold, CircleShape)
                        ) {
                            Icon(
                                imageVector        = Icons.Default.PlayArrow,
                                contentDescription = "Play",
                                tint               = BackgroundDark,
                                modifier           = Modifier.size(28.dp)
                            )
                        }
                    }

                    // Premium overlay
                    if (video.accessType == AccessType.PREMIUM && !video.isUnlocked) {
                        PremiumOverlay(onUnlock = onUnlock, price = video.price)
                    }

                    // FREE badge
                    if (video.accessType == AccessType.FREE) {
                        Surface(
                            modifier = Modifier
                                .align(Alignment.TopStart)
                                .padding(10.dp),
                            shape = RoundedCornerShape(6.dp),
                            color = AccentGreen.copy(alpha = 0.9f)
                        ) {
                            Text(
                                text     = "FREE",
                                style    = MaterialTheme.typography.labelSmall,
                                color    = Color.White,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                            )
                        }
                    }
                }
            }

            // ── Content area ─────────────────────────────────────────────
            Column(modifier = Modifier.padding(14.dp)) {

                // Expert row
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier          = Modifier.fillMaxWidth()
                ) {
                    AsyncImage(
                        model              = video.expert.photoUrl,
                        contentDescription = video.expert.name,
                        contentScale       = ContentScale.Crop,
                        modifier           = Modifier
                            .size(38.dp)
                            .clip(CircleShape)
                            .border(1.5.dp, PrimaryGold, CircleShape)
                    )

                    Spacer(modifier = Modifier.width(10.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text     = video.expert.name,
                            style    = MaterialTheme.typography.titleMedium,
                            color    = TextPrimary,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Text(
                            text  = video.expert.title,
                            style = MaterialTheme.typography.labelMedium,
                            color = TextSecondary
                        )
                    }

                    // Follow button
                    OutlinedButton(
                        onClick      = onFollow,
                        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 4.dp),
                        shape        = RoundedCornerShape(20.dp),
                        border       = BorderStroke(
                            1.dp,
                            if (video.expert.isFollowed) PrimaryGold else DividerColor
                        ),
                        modifier     = Modifier.height(30.dp)
                    ) {
                        Text(
                            text  = if (video.expert.isFollowed) "Following" else "Follow",
                            style = MaterialTheme.typography.labelMedium,
                            color = if (video.expert.isFollowed) PrimaryGold else TextSecondary
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Video title
                Text(
                    text     = video.videoTitle,
                    style    = MaterialTheme.typography.titleLarge,
                    color    = TextPrimary,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(4.dp))

                // Description
                Text(
                    text     = video.description,
                    style    = MaterialTheme.typography.bodyMedium,
                    color    = TextSecondary,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(4.dp))

                // Views
                Text(
                    text  = "${video.views} views",
                    style = MaterialTheme.typography.labelSmall,
                    color = TextHint
                )

                Spacer(modifier = Modifier.height(12.dp))
                Divider(color = DividerColor)
                Spacer(modifier = Modifier.height(12.dp))

                // Action buttons row
                Row(
                    modifier            = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment   = Alignment.CenterVertically
                ) {
                    // Like
                    IconButton(onClick = onLike) {
                        Icon(
                            imageVector = if (video.isLiked) Icons.Filled.Favorite
                            else Icons.Outlined.FavoriteBorder,
                            contentDescription = "Like",
                            tint = if (video.isLiked) AccentRed else TextSecondary,
                            modifier = Modifier.size(22.dp)
                        )
                    }

                    // Save
                    IconButton(onClick = onSave) {
                        Icon(
                            imageVector = if (video.isSaved) Icons.Filled.Bookmark
                            else Icons.Outlined.BookmarkBorder,
                            contentDescription = "Save",
                            tint = if (video.isSaved) PrimaryGold else TextSecondary,
                            modifier = Modifier.size(22.dp)
                        )
                    }

                    // Smart AI
                    OutlinedButton(
                        onClick        = onSmart,
                        shape          = RoundedCornerShape(20.dp),
                        border         = BorderStroke(1.dp, AccentBlue),
                        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp)
                    ) {
                        Icon(
                            imageVector        = Icons.Default.AutoAwesome,
                            contentDescription = "Smart",
                            tint               = AccentBlue,
                            modifier           = Modifier.size(14.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text  = "AI Insights",
                            style = MaterialTheme.typography.labelMedium,
                            color = AccentBlue
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                // View Profile + Book Session
                Row(
                    modifier              = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OutlinedButton(
                        onClick  = onViewProfile,
                        modifier = Modifier.weight(1f),
                        shape    = RoundedCornerShape(10.dp),
                        border   = BorderStroke(1.dp, DividerColor)
                    ) {
                        Text(
                            text  = "View Profile",
                            style = MaterialTheme.typography.labelLarge,
                            color = TextSecondary
                        )
                    }

                    Button(
                        onClick  = { /* Book Session */ },
                        modifier = Modifier.weight(1f),
                        shape    = RoundedCornerShape(10.dp),
                        colors   = ButtonDefaults.buttonColors(
                            containerColor = PrimaryGold
                        )
                    ) {
                        Text(
                            text  = "Book Session",
                            style = MaterialTheme.typography.labelLarge,
                            color = BackgroundDark
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun VideoPlayer(videoUrl: String) {
    val context = LocalContext.current
    val exoPlayer = remember {
        ExoPlayer.Builder(context).build().apply {
            setMediaItem(MediaItem.fromUri(videoUrl))
            prepare()
            playWhenReady = true
        }
    }

    DisposableEffect(Unit) {
        onDispose { exoPlayer.release() }
    }

    AndroidView(
        factory = {
            PlayerView(it).apply {
                player                = exoPlayer
                useController         = true
                layoutParams          = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
            }
        },
        modifier = Modifier.fillMaxSize()
    )
}