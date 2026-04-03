package com.taskplayer.core.theme

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Brush

val GradientStart    = Color(0xFF6C3AE8)
val GradientMid      = Color(0xFF4A6CF7)
val GradientEnd      = Color(0xFF38C2FF)

val BackgroundDark   = Color(0xFF222847)
val SurfaceDark      = Color(0xFF32313F)
val CardDark         = Color(0xFF1E1D33)
val CardElevated     = Color(0xFF252440)

val TextPrimary      = Color(0xFFF2F2FF)
val TextSecondary    = Color(0xFFABABC8)
val TextHint         = Color(0xFF5C5C7A)

val AccentPurple     = Color(0xFF8B5CF6)
val AccentBlue       = Color(0xFF4A6CF7)
val AccentCyan       = Color(0xFF38C2FF)
val AccentGreen      = Color(0xFF22C55E)
val AccentRed        = Color(0xFFFF5C7A)
val AccentYellow     = Color(0xFFFFD93D)

val PremiumGold      = Color(0xFFFFD93D)
val PremiumBadgeBg   = Color(0xFF2A2510)

val DividerColor     = Color(0xFF2A2840)


val GradientBrush = Brush.horizontalGradient(listOf(GradientStart, GradientMid))
val GradientSweepBrush = Brush.sweepGradient(listOf(GradientStart, GradientEnd))
val GradientRadialBrush = Brush.radialGradient(listOf(GradientStart, GradientMid))
val FollowActiveBrush = Brush.horizontalGradient(listOf(GradientStart, GradientMid))
val FollowInactiveBrush = Brush.horizontalGradient(listOf(CardElevated, CardElevated))
val AiBrush = Brush.horizontalGradient(listOf(AccentPurple, AccentBlue))