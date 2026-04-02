package com.taskplayer.features.feed

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.taskplayer.data.model.Video
import com.taskplayer.data.repository.VideoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

data class FeedUiState(
    val isLoading: Boolean       = true,
    val videos: List<Video>      = emptyList(),
    val smartSheetVideoId: String? = null
)

class FeedViewModel(private val repository: VideoRepository) : ViewModel() {

    private val _uiState = MutableStateFlow(FeedUiState())
    val uiState: StateFlow<FeedUiState> = _uiState

    init {
        loadVideos()
    }

    private fun loadVideos() {
        viewModelScope.launch {
            repository.simulateLoading()
            repository.videos.stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())
                .collect { videos ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        videos    = videos
                    )
                }
        }
    }

    fun toggleLike(videoId: String)   = repository.toggleLike(videoId)
    fun toggleSave(videoId: String)   = repository.toggleSave(videoId)
    fun unlockVideo(videoId: String)  = repository.unlockVideo(videoId)
    fun toggleFollow(expertId: String) = repository.toggleFollow(expertId)

    fun openSmartSheet(videoId: String) {
        _uiState.value = _uiState.value.copy(smartSheetVideoId = videoId)
    }

    fun closeSmartSheet() {
        _uiState.value = _uiState.value.copy(smartSheetVideoId = null)
    }
}