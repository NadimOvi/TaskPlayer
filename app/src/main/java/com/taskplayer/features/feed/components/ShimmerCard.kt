package com.taskplayer.features.feed.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun ShimmerCard() {
    val transition = rememberInfiniteTransition(label = "shimmer")
    val x by transition.animateFloat(
        initialValue  = 0f,
        targetValue   = 1200f,
        animationSpec = infiniteRepeatable(tween(1000, easing = LinearEasing), RepeatMode.Restart),
        label         = "x"
    )

    val brush = Brush.linearGradient(
        colors = listOf(Color(0xFF1E1D33), Color(0xFF2E2C4A), Color(0xFF1E1D33)),
        start  = Offset(x - 300f, 0f),
        end    = Offset(x, 0f)
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .background(Color(0xFF1E1D33))
    ) {
        Box(modifier = Modifier.fillMaxWidth().height(210.dp).background(brush))
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = androidx.compose.ui.Alignment.CenterVertically) {
                Box(Modifier.size(42.dp).clip(CircleShape).background(brush))
                Spacer(Modifier.width(10.dp))
                Column {
                    Box(Modifier.width(150.dp).height(14.dp).clip(RoundedCornerShape(4.dp)).background(brush))
                    Spacer(Modifier.height(6.dp))
                    Box(Modifier.width(100.dp).height(10.dp).clip(RoundedCornerShape(4.dp)).background(brush))
                }
            }
            Spacer(Modifier.height(14.dp))
            Box(Modifier.fillMaxWidth().height(16.dp).clip(RoundedCornerShape(4.dp)).background(brush))
            Spacer(Modifier.height(8.dp))
            Box(Modifier.fillMaxWidth(0.65f).height(12.dp).clip(RoundedCornerShape(4.dp)).background(brush))
            Spacer(Modifier.height(16.dp))
            Box(Modifier.fillMaxWidth().height(40.dp).clip(RoundedCornerShape(12.dp)).background(brush))
        }
    }
}