package com.taskplayer.data.model

data class Expert(
    val id: String,
    val name: String,
    val title: String,
    val bio: String,
    val photoUrl: String,
    val followers: Int,
    val rating: Float,
    val totalSessions: Int,
    val isFollowed: Boolean = false
)