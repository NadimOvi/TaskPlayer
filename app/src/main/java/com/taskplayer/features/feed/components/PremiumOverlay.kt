package com.taskplayer.features.feed.components

import androidx.compose.animation.*
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.taskplayer.core.theme.*
import kotlinx.coroutines.delay

@Composable
fun PremiumOverlay(
    onUnlock: () -> Unit,
    price: Double
) {
    var isUnlocking by remember { mutableStateOf(false) }
    var unlocked    by remember { mutableStateOf(false) }

    LaunchedEffect(isUnlocking) {
        if (isUnlocking) {
            delay(1200)
            unlocked = true
            onUnlock()
        }
    }

    Box(
        modifier          = Modifier
            .fillMaxSize()
            .background(PremiumOverlay),
        contentAlignment  = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Premium badge
            Surface(
                shape = RoundedCornerShape(8.dp),
                color = PremiumBadgeBg
            ) {
                Row(
                    verticalAlignment  = Alignment.CenterVertically,
                    modifier           = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                ) {
                    Text(text = "⭐", style = MaterialTheme.typography.labelMedium)
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text  = "PREMIUM",
                        style = MaterialTheme.typography.labelLarge,
                        color = PremiumGold
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Lock icon animated
            AnimatedContent(
                targetState = isUnlocking,
                label       = "lock_anim"
            ) { unlocking ->
                Icon(
                    imageVector        = if (unlocking) Icons.Default.LockOpen
                    else Icons.Default.Lock,
                    contentDescription = "Lock",
                    tint               = if (unlocking) AccentGreen else PrimaryGold,
                    modifier           = Modifier.size(36.dp)
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text  = "$${"%.2f".format(price)} to unlock",
                style = MaterialTheme.typography.labelMedium,
                color = TextSecondary
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Unlock button
            Button(
                onClick  = { isUnlocking = true },
                enabled  = !isUnlocking,
                shape    = RoundedCornerShape(10.dp),
                colors   = ButtonDefaults.buttonColors(
                    containerColor = PrimaryGold
                )
            ) {
                if (isUnlocking) {
                    CircularProgressIndicator(
                        modifier  = Modifier.size(16.dp),
                        color     = BackgroundDark,
                        strokeWidth = 2.dp
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text  = "Unlocking...",
                        color = BackgroundDark,
                        style = MaterialTheme.typography.labelLarge
                    )
                } else {
                    Text(
                        text  = "Unlock Video",
                        color = BackgroundDark,
                        style = MaterialTheme.typography.labelLarge
                    )
                }
            }
        }
    }
}