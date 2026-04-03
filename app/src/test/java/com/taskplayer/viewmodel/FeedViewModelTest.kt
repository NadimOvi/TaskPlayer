package com.taskplayer

import com.taskplayer.data.mock.MockExperts
import com.taskplayer.data.model.AccessType
import com.taskplayer.data.model.Video
import com.taskplayer.data.repository.VideoRepository
import com.taskplayer.features.feed.FeedViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class FeedViewModelTest {

    private val testDispatcher = StandardTestDispatcher()
    private lateinit var repository: VideoRepository
    private lateinit var viewModel: FeedViewModel

    private val fakeVideo1 = Video(
        id           = "v1",
        videoTitle   = "Health Video",
        description  = "About health",
        expert       = MockExperts.all[0],
        accessType   = AccessType.FREE,
        thumbnailUrl = "",
        videoUrl     = "",
        price        = 0.0,
        tags         = listOf("Health"),
        duration     = "5:00",
        views        = "10K"
    )

    private val fakeVideo2 = Video(
        id           = "v2",
        videoTitle   = "Business Video",
        description  = "About business",
        expert       = MockExperts.all[1],
        accessType   = AccessType.PREMIUM,
        thumbnailUrl = "",
        videoUrl     = "",
        price        = 9.99,
        tags         = listOf("Business"),
        duration     = "10:00",
        views        = "20K"
    )

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        repository = VideoRepository()
        repository.setVideosForTest(listOf(fakeVideo1, fakeVideo2))
        viewModel  = FeedViewModel(repository)
        testDispatcher.scheduler.advanceUntilIdle()
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `videos are loaded into state`() {
        assertTrue(viewModel.uiState.value.videos.isNotEmpty())
    }

    @Test
    fun `like video - shows as liked in state`() {
        viewModel.toggleLike("v1")
        val video = viewModel.uiState.value.videos.find { it.id == "v1" }
        assertTrue(video?.isLiked == true)
    }

    @Test
    fun `save video - shows as saved in state`() {
        viewModel.toggleSave("v2")
        val video = viewModel.uiState.value.videos.find { it.id == "v2" }
        assertTrue(video?.isSaved == true)
    }

    @Test
    fun `unlock video - shows as unlocked in state`() {
        viewModel.unlockVideo("v2")
        val video = viewModel.uiState.value.videos.find { it.id == "v2" }
        assertTrue(video?.isUnlocked == true)
    }

    @Test
    fun `open smart sheet - sets smartSheetVideoId`() {
        viewModel.openSmartSheet("v1")
        assertEquals("v1", viewModel.uiState.value.smartSheetVideoId)
    }

    @Test
    fun `close smart sheet - clears smartSheetVideoId`() {
        viewModel.openSmartSheet("v1")
        viewModel.closeSmartSheet()
        assertNull(viewModel.uiState.value.smartSheetVideoId)
    }

    @Test
    fun `follow expert - shows as followed in state`() {
        val expertId = MockExperts.all[0].id
        viewModel.toggleFollow(expertId)
        val video = viewModel.uiState.value.videos.find { it.expert.id == expertId }
        assertTrue(video?.expert?.isFollowed == true)
    }

    @Test
    fun `free video accessType is FREE`() {
        val video = viewModel.uiState.value.videos.find { it.id == "v1" }
        assertEquals(AccessType.FREE, video?.accessType)
    }

    @Test
    fun `premium video accessType is PREMIUM`() {
        val video = viewModel.uiState.value.videos.find { it.id == "v2" }
        assertEquals(AccessType.PREMIUM, video?.accessType)
    }

    @Test
    fun `premium video is locked by default`() {
        val video = viewModel.uiState.value.videos.find { it.id == "v2" }
        assertFalse(video?.isUnlocked == true)
    }
}