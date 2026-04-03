package com.taskplayer

import com.taskplayer.data.mock.MockExperts
import com.taskplayer.data.model.AccessType
import com.taskplayer.data.model.Video
import com.taskplayer.data.repository.VideoRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class VideoRepositoryTest {

    private lateinit var repository: VideoRepository

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
        repository = VideoRepository()
        repository.setVideosForTest(listOf(fakeVideo1, fakeVideo2))
    }

    @Test
    fun `like video - isLiked becomes true`() = runTest {
        repository.toggleLike("v1")
        val video = repository.videos.first().find { it.id == "v1" }
        assertTrue(video?.isLiked == true)
    }

    @Test
    fun `like video twice - isLiked goes back to false`() = runTest {
        repository.toggleLike("v1")
        repository.toggleLike("v1")
        val video = repository.videos.first().find { it.id == "v1" }
        assertFalse(video?.isLiked == true)
    }

    @Test
    fun `save video - isSaved becomes true`() = runTest {
        repository.toggleSave("v2")
        val video = repository.videos.first().find { it.id == "v2" }
        assertTrue(video?.isSaved == true)
    }

    @Test
    fun `unsave video - isSaved goes back to false`() = runTest {
        repository.toggleSave("v2")
        repository.toggleSave("v2")
        val video = repository.videos.first().find { it.id == "v2" }
        assertFalse(video?.isSaved == true)
    }

    @Test
    fun `savedVideos only shows saved videos`() = runTest {
        repository.toggleSave("v1")
        val saved = repository.savedVideos.first()
        assertEquals(1, saved.size)
        assertEquals("v1", saved.first().id)
    }

    @Test
    fun `unlock premium video - isUnlocked becomes true`() = runTest {
        repository.unlockVideo("v2")
        val video = repository.videos.first().find { it.id == "v2" }
        assertTrue(video?.isUnlocked == true)
    }

    @Test
    fun `unlock does not affect other videos`() = runTest {
        repository.unlockVideo("v2")
        val video = repository.videos.first().find { it.id == "v1" }
        assertFalse(video?.isUnlocked == true)
    }

    @Test
    fun `follow expert - isFollowed becomes true`() = runTest {
        val expertId = MockExperts.all[0].id
        repository.toggleFollow(expertId)
        val video = repository.videos.first().find { it.expert.id == expertId }
        assertTrue(video?.expert?.isFollowed == true)
    }

    @Test
    fun `get videos by expert - returns correct videos`() = runTest {
        val expertId = MockExperts.all[0].id
        val result   = repository.getVideosByExpert(expertId)
        assertTrue(result.all { it.expert.id == expertId })
    }

    @Test
    fun `get expert by id - returns correct expert`() {
        val expert = repository.getExpertById(MockExperts.all[0].id)
        assertEquals(MockExperts.all[0].name, expert?.name)
    }

    @Test
    fun `get expert by wrong id - returns null`() {
        val expert = repository.getExpertById("wrong_id")
        assertNull(expert)
    }

    @Test
    fun `premium video has price greater than zero`() = runTest {
        val video = repository.videos.first().find { it.id == "v2" }
        assertTrue((video?.price ?: 0.0) > 0.0)
    }

    @Test
    fun `free video has price of zero`() = runTest {
        val video = repository.videos.first().find { it.id == "v1" }
        assertEquals(0.0, video?.price)
    }
}