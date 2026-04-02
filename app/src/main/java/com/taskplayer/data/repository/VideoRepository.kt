package com.taskplayer.data.repository

import com.taskplayer.data.mock.MockData
import com.taskplayer.data.model.Expert
import com.taskplayer.data.model.Video
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map

class VideoRepository {

    private val _videos = MutableStateFlow(MockData.videos)
    val videos: Flow<List<Video>> = _videos.asStateFlow()

    private val _experts = MutableStateFlow(MockData.experts)

    val savedVideos: Flow<List<Video>> = _videos.map { list ->
        list.filter { it.isSaved }
    }

    suspend fun simulateLoading() {
        delay(1500)
    }

    fun toggleLike(videoId: String) {
        _videos.value = _videos.value.map { video ->
            if (video.id == videoId) video.copy(isLiked = !video.isLiked)
            else video
        }
    }

    fun toggleSave(videoId: String) {
        _videos.value = _videos.value.map { video ->
            if (video.id == videoId) video.copy(isSaved = !video.isSaved)
            else video
        }
    }

    fun unlockVideo(videoId: String) {
        _videos.value = _videos.value.map { video ->
            if (video.id == videoId) video.copy(isUnlocked = true)
            else video
        }
    }

    fun toggleFollow(expertId: String) {
        val updatedExperts = _experts.value.map { expert ->
            if (expert.id == expertId) expert.copy(isFollowed = !expert.isFollowed)
            else expert
        }
        _experts.value = updatedExperts
        _videos.value = _videos.value.map { video ->
            if (video.expert.id == expertId)
                video.copy(expert = video.expert.copy(
                    isFollowed = !video.expert.isFollowed
                ))
            else video
        }
    }

    fun getVideoById(videoId: String): Video? =
        _videos.value.find { it.id == videoId }

    fun getExpertById(expertId: String): Expert? =
        _experts.value.find { it.id == expertId }

    fun getVideosByExpert(expertId: String): List<Video> =
        _videos.value.filter { it.expert.id == expertId }
}