package com.taskplayer

import com.taskplayer.data.mock.MockExperts
import com.taskplayer.data.model.AccessType
import com.taskplayer.data.model.Video
import com.taskplayer.data.repository.VideoRepository
import com.taskplayer.features.saved.SavedViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class SavedViewModelTest {

    private val testDispatcher = StandardTestDispatcher()
    private lateinit var repository: VideoRepository
    private lateinit var viewModel: SavedViewModel

    private val fakeVideo1 = Video(
        id           = "v1",
        videoTitle   = "Mindfulness Video",
        description  = "Calm your mind",
        expert       = MockExperts.all[4],
        accessType   = AccessType.FREE,
        thumbnailUrl = "",
        videoUrl     = "",
        price        = 0.0,
        tags         = listOf("Mindfulness"),
        duration     = "5:00",
        views        = "89K"
    )

    private val fakeVideo2 = Video(
        id           = "v2",
        videoTitle   = "Sleep Science",
        description  = "Sleep better",
        expert       = MockExperts.all[0],
        accessType   = AccessType.PREMIUM,
        thumbnailUrl = "",
        videoUrl     = "",
        price        = 9.99,
        tags         = listOf("Sleep"),
        duration     = "27:00",
        views        = "33K"
    )

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        repository = VideoRepository()
        repository.setVideosForTest(listOf(fakeVideo1, fakeVideo2))
        viewModel  = SavedViewModel(repository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `saved list is empty at start`() = runTest {
        val saved = viewModel.savedVideos.first()
        assertTrue(saved.isEmpty())
    }

    @Test
    fun `save one video - saved list has one item`() = runTest {
        repository.toggleSave("v1")
        testDispatcher.scheduler.advanceUntilIdle()
        val saved = viewModel.savedVideos.first()
        assertEquals(1, saved.size)
    }

    @Test
    fun `save two videos - saved list has two items`() = runTest {
        repository.toggleSave("v1")
        repository.toggleSave("v2")
        testDispatcher.scheduler.advanceUntilIdle()
        val saved = viewModel.savedVideos.first()
        assertEquals(2, saved.size)
    }

    @Test
    fun `unsave video - removed from saved list`() = runTest {
        repository.toggleSave("v1")
        repository.toggleSave("v1")
        testDispatcher.scheduler.advanceUntilIdle()
        val saved = viewModel.savedVideos.first()
        assertTrue(saved.none { it.id == "v1" })
    }

    @Test
    fun `all items in saved list have isSaved true`() = runTest {
        repository.toggleSave("v1")
        repository.toggleSave("v2")
        testDispatcher.scheduler.advanceUntilIdle()
        val saved = viewModel.savedVideos.first()
        assertTrue(saved.all { it.isSaved })
    }

    @Test
    fun `saving premium video works correctly`() = runTest {
        repository.toggleSave("v2")
        testDispatcher.scheduler.advanceUntilIdle()
        val saved = viewModel.savedVideos.first()
        val video = saved.find { it.id == "v2" }
        assertEquals(AccessType.PREMIUM, video?.accessType)
        assertTrue(video?.isSaved == true)
    }
}