package com.taskplayer.core.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

val TaskPlayerTypography = Typography(

    headlineLarge = TextStyle(
        fontWeight    = FontWeight.ExtraBold,
        fontSize      = 28.sp,
        letterSpacing = (-0.5).sp,
        color         = TextPrimary
    ),

    headlineMedium = TextStyle(
        fontWeight    = FontWeight.Bold,
        fontSize      = 22.sp,
        letterSpacing = (-0.3).sp,
        color         = TextPrimary
    ),

    titleLarge = TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize   = 18.sp,
        color      = TextPrimary
    ),

    titleMedium = TextStyle(
        fontWeight = FontWeight.SemiBold,
        fontSize   = 15.sp,
        color      = TextPrimary
    ),

    bodyLarge = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize   = 15.sp,
        lineHeight = 23.sp,
        color      = TextSecondary
    ),

    bodyMedium = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize   = 13.sp,
        lineHeight = 20.sp,
        color      = TextSecondary
    ),

    labelLarge = TextStyle(
        fontWeight    = FontWeight.Bold,
        fontSize      = 13.sp,
        letterSpacing = 0.3.sp,
        color         = TextPrimary
    ),

    labelMedium = TextStyle(
        fontWeight    = FontWeight.SemiBold,
        fontSize      = 11.sp,
        letterSpacing = 0.4.sp,
        color         = TextSecondary
    ),

    labelSmall = TextStyle(
        fontWeight    = FontWeight.Medium,
        fontSize      = 10.sp,
        letterSpacing = 0.5.sp,
        color         = TextHint
    )
)