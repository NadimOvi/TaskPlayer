package com.taskplayer.features.feed.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.dp
import com.taskplayer.core.theme.*
import com.taskplayer.data.model.Video
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun SmartSummarySheet(video: Video, onDismiss: () -> Unit) {
    var isLoading   by remember { mutableStateOf(true) }
    var showContent by remember { mutableStateOf(false) }

    val summary = "This session by ${video.expert.name} covers ${video.videoTitle} " +
            "with a practical, expert-backed approach. Viewers gain clear, " +
            "actionable insights they can apply immediately for real results."

    LaunchedEffect(Unit) {
        delay(900)
        isLoading = false
        delay(200)
        showContent = true
    }

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        containerColor   = CardDark,
        shape            = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 8.dp)
                .padding(bottom = 36.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .background(
                            Brush.radialGradient(listOf(AccentPurple, AccentBlue)),
                            RoundedCornerShape(10.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        Icons.Default.AutoAwesome,
                        contentDescription = null,
                        tint               = TextPrimary,
                        modifier           = Modifier.size(18.dp)
                    )
                }
                Spacer(Modifier.width(10.dp))
                Column {
                    Text("AI Insights", style = MaterialTheme.typography.titleLarge)
                    Text(
                        text  = video.videoTitle,
                        style = MaterialTheme.typography.labelSmall,
                        maxLines = 1
                    )
                }
            }

            Spacer(Modifier.height(20.dp))

            if (isLoading) {
                Box(Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = AccentPurple, modifier = Modifier.size(32.dp))
                }
            } else {
                AnimatedVisibility(
                    visible = showContent,
                    enter   = fadeIn() + slideInVertically()
                ) {
                    Column {
                        Text(
                            text  = "Key Topics",
                            style = MaterialTheme.typography.labelLarge,
                            color = AccentCyan
                        )
                        Spacer(Modifier.height(10.dp))
                        FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            video.tags.forEach { tag ->
                                Surface(
                                    shape = RoundedCornerShape(20.dp),
                                    color = CardElevated
                                ) {
                                    Text(
                                        text     = "# $tag",
                                        style    = MaterialTheme.typography.labelMedium,
                                        color    = AccentPurple,
                                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                                    )
                                }
                            }
                        }

                        Spacer(Modifier.height(20.dp))

                        Text(
                            text  = "Smart Summary",
                            style = MaterialTheme.typography.labelLarge,
                            color = AccentCyan
                        )
                        Spacer(Modifier.height(10.dp))
                        Surface(
                            shape = RoundedCornerShape(14.dp),
                            color = CardElevated
                        ) {
                            Text(
                                text     = summary,
                                style    = MaterialTheme.typography.bodyMedium,
                                modifier = Modifier.padding(14.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}