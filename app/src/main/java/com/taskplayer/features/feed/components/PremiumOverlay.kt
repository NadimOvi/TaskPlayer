package com.taskplayer.features.feed.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.LockOpen
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.dp
import com.taskplayer.core.theme.*
import kotlinx.coroutines.delay

@Composable
fun PremiumOverlay(onUnlock: () -> Unit, price: Double) {
    var isUnlocking by remember { mutableStateOf(false) }

    LaunchedEffect(isUnlocking) {
        if (isUnlocking) {
            delay(1200)
            onUnlock()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(BackgroundDark.copy(alpha = 0.6f), BackgroundDark.copy(alpha = 0.95f))
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {

            Surface(
                shape = RoundedCornerShape(10.dp),
                color = PremiumBadgeBg
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier          = Modifier.padding(horizontal = 14.dp, vertical = 7.dp)
                ) {
                    Text("⭐", style = MaterialTheme.typography.labelMedium)
                    Spacer(Modifier.width(4.dp))
                    Text(
                        text  = "PREMIUM",
                        style = MaterialTheme.typography.labelLarge,
                        color = PremiumGold
                    )
                }
            }

            Spacer(Modifier.height(12.dp))

            AnimatedContent(targetState = isUnlocking, label = "lock") { unlocking ->
                Icon(
                    imageVector        = if (unlocking) Icons.Default.LockOpen else Icons.Default.Lock,
                    contentDescription = null,
                    tint               = if (unlocking) AccentGreen else PremiumGold,
                    modifier           = Modifier.size(38.dp)
                )
            }

            Spacer(Modifier.height(6.dp))

            Text(
                text  = "$${"%.2f".format(price)} to unlock",
                style = MaterialTheme.typography.labelMedium,
                color = TextSecondary
            )

            Spacer(Modifier.height(14.dp))

            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(12.dp))
                    .background(Brush.horizontalGradient(listOf(GradientStart, GradientMid)))
            ) {
                Button(
                    onClick   = { isUnlocking = true },
                    enabled   = !isUnlocking,
                    shape     = RoundedCornerShape(12.dp),
                    colors    = ButtonDefaults.buttonColors(containerColor = androidx.compose.ui.graphics.Color.Transparent),
                    elevation = ButtonDefaults.buttonElevation(0.dp)
                ) {
                    if (isUnlocking) {
                        CircularProgressIndicator(
                            modifier    = Modifier.size(16.dp),
                            color       = TextPrimary,
                            strokeWidth = 2.dp
                        )
                        Spacer(Modifier.width(8.dp))
                        Text("Unlocking...", color = TextPrimary, style = MaterialTheme.typography.labelLarge)
                    } else {
                        Text("Unlock Video", color = TextPrimary, style = MaterialTheme.typography.labelLarge)
                    }
                }
            }
        }
    }
}