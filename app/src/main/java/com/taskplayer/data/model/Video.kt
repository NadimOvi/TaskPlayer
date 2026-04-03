package com.taskplayer.data.model

enum class AccessType { FREE, PREMIUM }

data class Video(
    val id: String,
    val videoTitle: String,
    val description: String,
    val expert: Expert,
    val accessType: AccessType,
    val thumbnailUrl: String,
    val videoUrl: String,
    val price: Double,
    val tags: List<String>,
    val duration: String,
    val views: String,
    val isUnlocked: Boolean = false,
    val isLiked: Boolean    = false,
    val isSaved: Boolean    = false
)