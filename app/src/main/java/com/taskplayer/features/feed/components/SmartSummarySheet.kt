package com.taskplayer.features.feed.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.taskplayer.core.theme.*
import com.taskplayer.data.model.Video
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun SmartSummarySheet(
    video: Video,
    onDismiss: () -> Unit
) {
    var isLoading  by remember { mutableStateOf(true) }
    var showTopics by remember { mutableStateOf(false) }
    var showSummary by remember { mutableStateOf(false) }

    val mockSummary = "This session by ${video.expert.name} explores " +
            "${video.videoTitle.lowercase()} with a focus on practical, " +
            "evidence-based strategies. Viewers will walk away with clear " +
            "actionable steps they can apply immediately to see real results."

    LaunchedEffect(Unit) {
        delay(1000)
        isLoading   = false
        showTopics  = true
        delay(300)
        showSummary = true
    }

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        containerColor   = CardDark,
        shape            = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 8.dp)
                .padding(bottom = 32.dp)
        ) {
            // Header
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector        = Icons.Default.AutoAwesome,
                    contentDescription = "AI",
                    tint               = AccentBlue,
                    modifier           = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text  = "AI Insights",
                    style = MaterialTheme.typography.titleLarge,
                    color = TextPrimary
                )
            }

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text  = video.videoTitle,
                style = MaterialTheme.typography.bodyMedium,
                color = TextSecondary
            )

            Spacer(modifier = Modifier.height(20.dp))

            if (isLoading) {
                Box(
                    modifier          = Modifier.fillMaxWidth(),
                    contentAlignment  = Alignment.Center
                ) {
                    CircularProgressIndicator(color = AccentBlue)
                }
            } else {

                // Key Topics
                AnimatedVisibility(
                    visible = showTopics,
                    enter   = fadeIn()
                ) {
                    Column {
                        Text(
                            text  = "Key Topics",
                            style = MaterialTheme.typography.labelLarge,
                            color = PrimaryGold
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            video.tags.forEach { tag ->
                                Surface(
                                    shape = RoundedCornerShape(20.dp),
                                    color = CardDarkElevated
                                ) {
                                    Text(
                                        text     = "# $tag",
                                        style    = MaterialTheme.typography.labelMedium,
                                        color    = AccentBlue,
                                        modifier = Modifier.padding(
                                            horizontal = 12.dp, vertical = 6.dp
                                        )
                                    )
                                }
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                // Smart Summary
                AnimatedVisibility(
                    visible = showSummary,
                    enter   = fadeIn()
                ) {
                    Column {
                        Text(
                            text  = "Smart Summary",
                            style = MaterialTheme.typography.labelLarge,
                            color = PrimaryGold
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = CardDarkElevated
                        ) {
                            Text(
                                text     = mockSummary,
                                style    = MaterialTheme.typography.bodyMedium,
                                color    = TextSecondary,
                                modifier = Modifier.padding(14.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}