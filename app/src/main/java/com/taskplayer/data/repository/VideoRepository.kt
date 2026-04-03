package com.taskplayer.data.repository

import com.taskplayer.data.mock.MockExperts
import com.taskplayer.data.mock.MockTitles
import com.taskplayer.data.model.AccessType
import com.taskplayer.data.model.Expert
import com.taskplayer.data.model.Video
import com.taskplayer.data.remote.PexelsApiService
import com.taskplayer.data.remote.PexelsVideo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map

class VideoRepository {

    private val apiService = PexelsApiService.create()

    private val _videos = MutableStateFlow<List<Video>>(emptyList())
    val videos: Flow<List<Video>> = _videos.asStateFlow()

    val savedVideos: Flow<List<Video>> = _videos.map { list ->
        list.filter { it.isSaved }
    }

    suspend fun fetchVideos() {
        val response = apiService.searchVideos(query = "expert learning education talk")

        val fetched = response.videos.mapIndexed { index, pexelsVideo ->
            val expert    = MockExperts.forCategory(index)
            val mp4Url    = getBestMp4(pexelsVideo)
            val title     = MockTitles.titles.getOrElse(index) { "Expert Session ${index + 1}" }
            val desc      = MockTitles.descriptions.getOrElse(index) { "An insightful expert session." }
            val tags      = MockTitles.tags.getOrElse(index) { listOf("Expert", "Learning") }
            val isPremium = index % 3 == 0

            Video(
                id           = pexelsVideo.id.toString(),
                videoTitle   = title,
                description  = desc,
                expert       = expert,
                accessType   = if (isPremium) AccessType.PREMIUM else AccessType.FREE,
                thumbnailUrl = pexelsVideo.thumbnail,
                videoUrl     = mp4Url,
                price        = if (isPremium) 9.99 else 0.0,
                tags         = tags,
                duration     = formatDuration(pexelsVideo.duration),
                views        = "${(10..999).random()}K"
            )
        }

        val existing = _videos.value.associateBy { it.id }
        _videos.value = fetched.map { new ->
            existing[new.id]?.let { old ->
                new.copy(
                    isLiked    = old.isLiked,
                    isSaved    = old.isSaved,
                    isUnlocked = old.isUnlocked
                )
            } ?: new
        }
    }

    private fun getBestMp4(video: PexelsVideo): String {
        val files = video.videoFiles.filter { it.fileType == "video/mp4" }
        return files.firstOrNull {
            it.quality == "hd" && (it.height ?: 0) > (it.width ?: 0)
        }?.link
            ?: files.firstOrNull { it.quality == "hd" }?.link
            ?: files.firstOrNull { it.quality == "sd" }?.link
            ?: files.firstOrNull()?.link
            ?: ""
    }

    private fun formatDuration(seconds: Int): String {
        val m = seconds / 60
        val s = seconds % 60
        return "%d:%02d".format(m, s)
    }

    fun toggleLike(videoId: String) {
        _videos.value = _videos.value.map {
            if (it.id == videoId) it.copy(isLiked = !it.isLiked) else it
        }
    }

    fun toggleSave(videoId: String) {
        _videos.value = _videos.value.map {
            if (it.id == videoId) it.copy(isSaved = !it.isSaved) else it
        }
    }

    fun unlockVideo(videoId: String) {
        _videos.value = _videos.value.map {
            if (it.id == videoId) it.copy(isUnlocked = true) else it
        }
    }

    fun toggleFollow(expertId: String) {
        _videos.value = _videos.value.map { video ->
            if (video.expert.id == expertId)
                video.copy(expert = video.expert.copy(isFollowed = !video.expert.isFollowed))
            else video
        }
    }

    fun getVideosByExpert(expertId: String): List<Video> =
        _videos.value.filter { it.expert.id == expertId }

    fun getExpertById(expertId: String): Expert? =
        MockExperts.all.find { it.id == expertId }
}