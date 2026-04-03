package com.taskplayer.features.feed.components

import android.view.ViewGroup
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
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.media3.common.AudioAttributes
import androidx.media3.common.C
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import coil.compose.AsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.taskplayer.core.theme.*
import com.taskplayer.data.model.AccessType
import com.taskplayer.data.model.Video

@OptIn(ExperimentalLayoutApi::class)
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
    var isPlaying by remember(video.id) { mutableStateOf(false) }
    val canPlay   = video.accessType == AccessType.FREE || video.isUnlocked
    val context   = LocalContext.current

    DisposableEffect(video.id) {
        onDispose { isPlaying = false }
    }

    Card(
        modifier  = Modifier.fillMaxWidth(),
        shape     = RoundedCornerShape(20.dp),
        colors    = CardDefaults.cardColors(containerColor = CardDark),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(210.dp)
            ) {
                if (isPlaying && canPlay) {
                    ExoPlayerView(
                        videoUrl = video.videoUrl,
                        modifier = Modifier.fillMaxSize()
                    )
                    IconButton(
                        onClick  = { isPlaying = false },
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .padding(8.dp)
                            .size(32.dp)
                            .background(Color(0xAA000000), CircleShape)
                    ) {
                        Icon(
                            imageVector        = Icons.Default.Close,
                            contentDescription = "Stop",
                            tint               = Color.White,
                            modifier           = Modifier.size(16.dp)
                        )
                    }
                } else {
                    AsyncImage(
                        model = ImageRequest.Builder(context)
                            .data(video.thumbnailUrl)
                            .memoryCachePolicy(CachePolicy.ENABLED)
                            .diskCachePolicy(CachePolicy.ENABLED)
                            .crossfade(false)
                            .size(800, 500)
                            .build(),
                        contentDescription = null,
                        contentScale       = ContentScale.Crop,
                        modifier           = Modifier.fillMaxSize()
                    )

                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                Brush.verticalGradient(
                                    colors = listOf(Color.Transparent, Color(0xDD0F0E1A)),
                                    startY = 60f
                                )
                            )
                    )

                    Surface(
                        modifier = Modifier
                            .align(Alignment.TopStart)
                            .padding(10.dp),
                        shape = RoundedCornerShape(8.dp),
                        color = if (video.accessType == AccessType.FREE)
                            AccentGreen.copy(alpha = 0.9f) else PremiumBadgeBg
                    ) {
                        Text(
                            text     = if (video.accessType == AccessType.FREE) "FREE" else "⭐ PREMIUM",
                            style    = MaterialTheme.typography.labelSmall,
                            color    = if (video.accessType == AccessType.FREE) Color.White else PremiumGold,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }

                    Surface(
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .padding(10.dp),
                        shape = RoundedCornerShape(6.dp),
                        color = Color(0xAA000000)
                    ) {
                        Text(
                            text     = video.duration,
                            style    = MaterialTheme.typography.labelSmall,
                            color    = TextPrimary,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 3.dp)
                        )
                    }

                    if (canPlay) {
                        Box(
                            modifier = Modifier
                                .align(Alignment.Center)
                                .size(58.dp)
                                .background(GradientRadialBrush, CircleShape)
                                .clickable { isPlaying = true },
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector        = Icons.Default.PlayArrow,
                                contentDescription = "Play",
                                tint               = Color.White,
                                modifier           = Modifier.size(32.dp)
                            )
                        }
                    }

                    if (video.accessType == AccessType.PREMIUM && !video.isUnlocked) {
                        PremiumOverlay(onUnlock = onUnlock, price = video.price)
                    }
                }
            }

            Column(modifier = Modifier.padding(16.dp)) {

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier          = Modifier.fillMaxWidth()
                ) {
                    AsyncImage(
                        model = ImageRequest.Builder(context)
                            .data(video.expert.photoUrl)
                            .memoryCachePolicy(CachePolicy.ENABLED)
                            .diskCachePolicy(CachePolicy.ENABLED)
                            .crossfade(false)
                            .size(120, 120)
                            .build(),
                        contentDescription = null,
                        contentScale       = ContentScale.Crop,
                        modifier           = Modifier
                            .size(42.dp)
                            .clip(CircleShape)
                            .border(2.dp, GradientSweepBrush, CircleShape)
                    )

                    Spacer(modifier = Modifier.width(10.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text     = video.expert.name,
                            style    = MaterialTheme.typography.titleMedium,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Text(
                            text  = video.expert.title,
                            style = MaterialTheme.typography.labelMedium
                        )
                    }

                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(20.dp))
                            .background(
                                if (video.expert.isFollowed) FollowActiveBrush
                                else FollowInactiveBrush
                            )
                            .clickable { onFollow() }
                            .padding(horizontal = 14.dp, vertical = 7.dp)
                    ) {
                        Text(
                            text  = if (video.expert.isFollowed) "✓ Following" else "+ Follow",
                            style = MaterialTheme.typography.labelMedium,
                            color = if (video.expert.isFollowed) TextPrimary else TextSecondary
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text     = video.videoTitle,
                    style    = MaterialTheme.typography.titleLarge,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text     = video.description,
                    style    = MaterialTheme.typography.bodyMedium,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(10.dp))

                FlowRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    video.tags.take(3).forEach { tag ->
                        key(tag) {
                            Surface(
                                shape = RoundedCornerShape(20.dp),
                                color = CardElevated
                            ) {
                                Text(
                                    text     = "# $tag",
                                    style    = MaterialTheme.typography.labelSmall,
                                    color    = AccentCyan,
                                    modifier = Modifier.padding(
                                        horizontal = 10.dp, vertical = 4.dp
                                    )
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))
                HorizontalDivider(color = DividerColor)
                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    modifier              = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment     = Alignment.CenterVertically
                ) {
                    IconButton(onClick = onLike) {
                        Icon(
                            imageVector        = if (video.isLiked) Icons.Filled.Favorite
                            else Icons.Outlined.FavoriteBorder,
                            contentDescription = "Like",
                            tint               = if (video.isLiked) AccentRed else TextSecondary,
                            modifier           = Modifier.size(22.dp)
                        )
                    }

                    IconButton(onClick = onSave) {
                        Icon(
                            imageVector        = if (video.isSaved) Icons.Filled.Bookmark
                            else Icons.Outlined.BookmarkBorder,
                            contentDescription = "Save",
                            tint               = if (video.isSaved) AccentYellow else TextSecondary,
                            modifier           = Modifier.size(22.dp)
                        )
                    }

                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(20.dp))
                            .background(
                                Brush.horizontalGradient(
                                    listOf(
                                        AccentPurple.copy(alpha = 0.2f),
                                        AccentBlue.copy(alpha = 0.2f)
                                    )
                                )
                            )
                            .clickable { onSmart() }
                            .padding(horizontal = 14.dp, vertical = 8.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector        = Icons.Default.AutoAwesome,
                                contentDescription = "AI",
                                tint               = AccentPurple,
                                modifier           = Modifier.size(14.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text  = "AI Insights",
                                style = MaterialTheme.typography.labelMedium,
                                color = AccentPurple
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier              = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OutlinedButton(
                        onClick  = onViewProfile,
                        modifier = Modifier.weight(1f),
                        shape    = RoundedCornerShape(12.dp),
                        border   = BorderStroke(1.dp, DividerColor)
                    ) {
                        Text(
                            text  = "View Profile",
                            style = MaterialTheme.typography.labelLarge,
                            color = TextSecondary
                        )
                    }

                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(12.dp))
                            .background(GradientBrush)
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
    }
}

@androidx.annotation.OptIn(androidx.media3.common.util.UnstableApi::class)
@Composable
fun ExoPlayerView(videoUrl: String, modifier: Modifier = Modifier) {
    val context        = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    val exoPlayer = remember(videoUrl) {
        ExoPlayer.Builder(context).build().apply {
            val audioAttributes = AudioAttributes.Builder()
                .setUsage(C.USAGE_MEDIA)
                .setContentType(C.AUDIO_CONTENT_TYPE_MOVIE)
                .build()
            setAudioAttributes(audioAttributes, true)
            setMediaItem(MediaItem.fromUri(videoUrl))
            repeatMode    = Player.REPEAT_MODE_ONE
            volume        = 1f
            prepare()
            playWhenReady = true
        }
    }

    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_RESUME -> {
                    exoPlayer.playWhenReady = true
                    exoPlayer.play()
                }
                Lifecycle.Event.ON_PAUSE  -> {
                    exoPlayer.playWhenReady = false
                    exoPlayer.pause()
                }
                else -> Unit
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
            exoPlayer.release()
        }
    }

    AndroidView(
        factory = { ctx ->
            PlayerView(ctx).apply {
                player        = exoPlayer
                useController = true
                layoutParams  = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
            }
        },
        modifier = modifier
    )
}