package com.taskplayer.features.feed

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.ImageLoader
import coil.request.ImageRequest
import com.taskplayer.data.model.Video
import com.taskplayer.data.repository.VideoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

data class FeedUiState(
    val isLoading: Boolean         = true,
    val videos: List<Video>        = emptyList(),
    val smartSheetVideoId: String? = null,
    val error: String?             = null
)

class FeedViewModel(
    private val repository: VideoRepository,
    private val imageLoader: ImageLoader,
    private val context: Context
) : ViewModel() {

    private val _uiState = MutableStateFlow(FeedUiState())
    val uiState: StateFlow<FeedUiState> = _uiState

    init {
        loadVideos()
    }

    private fun loadVideos() {
        viewModelScope.launch {
            try {
                repository.fetchVideos()
                val videos = repository.getCurrentVideos()

                withContext(Dispatchers.IO) {
                    videos.map { video ->
                        async {
                            imageLoader.execute(
                                ImageRequest.Builder(context)
                                    .data(video.thumbnailUrl)
                                    .build()
                            )
                            imageLoader.execute(
                                ImageRequest.Builder(context)
                                    .data(video.expert.photoUrl)
                                    .build()
                            )
                        }
                    }.awaitAll()
                }

                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    videos    = videos,
                    error     = null
                )

                observeVideos()

            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error     = "Could not load videos. Check your API key or connection."
                )
            }
        }
    }

    private fun observeVideos() {
        viewModelScope.launch {
            repository.videos.collect { videos ->
                if (!_uiState.value.isLoading) {
                    _uiState.value = _uiState.value.copy(videos = videos)
                }
            }
        }
    }

    fun toggleLike(videoId: String)    = repository.toggleLike(videoId)
    fun toggleSave(videoId: String)    = repository.toggleSave(videoId)
    fun unlockVideo(videoId: String)   = repository.unlockVideo(videoId)
    fun toggleFollow(expertId: String) = repository.toggleFollow(expertId)

    fun openSmartSheet(videoId: String) {
        _uiState.value = _uiState.value.copy(smartSheetVideoId = videoId)
    }

    fun closeSmartSheet() {
        _uiState.value = _uiState.value.copy(smartSheetVideoId = null)
    }
}