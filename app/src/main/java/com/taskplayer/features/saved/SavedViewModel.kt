package com.taskplayer.features.saved

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.taskplayer.data.model.Video
import com.taskplayer.data.repository.VideoRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

class SavedViewModel(repository: VideoRepository) : ViewModel() {
    val savedVideos: StateFlow<List<Video>> = repository.savedVideos
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
}