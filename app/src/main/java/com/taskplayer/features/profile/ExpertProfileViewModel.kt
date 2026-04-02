package com.taskplayer.features.profile

import androidx.lifecycle.ViewModel
import com.taskplayer.data.model.Expert
import com.taskplayer.data.model.Video
import com.taskplayer.data.repository.VideoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

data class ProfileUiState(
    val expert: Expert?      = null,
    val videos: List<Video>  = emptyList()
)

class ExpertProfileViewModel(
    private val repository: VideoRepository,
    private val expertId: String
) : ViewModel() {

    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState: StateFlow<ProfileUiState> = _uiState

    init {
        val expert = repository.getExpertById(expertId)
        val videos = repository.getVideosByExpert(expertId)
        _uiState.value = ProfileUiState(expert = expert, videos = videos)
    }

    fun toggleFollow() {
        repository.toggleFollow(expertId)
        val updated = repository.getExpertById(expertId)
        _uiState.value = _uiState.value.copy(expert = updated)
    }
}