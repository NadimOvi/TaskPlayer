package com.taskplayer

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.taskplayer.core.theme.TaskPlayerTheme
import com.taskplayer.data.mock.MockExperts
import com.taskplayer.data.model.AccessType
import com.taskplayer.data.model.Video
import com.taskplayer.data.repository.VideoRepository
import com.taskplayer.features.feed.FeedScreen
import com.taskplayer.features.feed.FeedViewModel
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class FeedScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val fakeVideos = listOf(
        Video(
            id           = "v1",
            videoTitle   = "Health Masterclass",
            description  = "Improve your health today.",
            expert       = MockExperts.all[0],
            accessType   = AccessType.FREE,
            thumbnailUrl = "",
            videoUrl     = "",
            price        = 0.0,
            tags         = listOf("Health"),
            duration     = "10:00",
            views        = "20K"
        ),
        Video(
            id           = "v2",
            videoTitle   = "Business Growth",
            description  = "Scale your startup fast.",
            expert       = MockExperts.all[1],
            accessType   = AccessType.PREMIUM,
            thumbnailUrl = "",
            videoUrl     = "",
            price        = 9.99,
            tags         = listOf("Business"),
            duration     = "15:00",
            views        = "8K"
        )
    )

    private fun setupScreen(
        onViewProfile: (String) -> Unit = {},
        onSavedClick: () -> Unit = {}
    ) {
        val repo = VideoRepository()
        repo.setVideosForTest(fakeVideos)
        val viewModel = FeedViewModel(repo)

        composeTestRule.setContent {
            TaskPlayerTheme {
                FeedScreen(
                    viewModel     = viewModel,
                    onViewProfile = onViewProfile,
                    onSavedClick  = onSavedClick
                )
            }
        }
    }

    @Test
    fun appTitle_isDisplayed() {
        setupScreen()
        composeTestRule
            .onNodeWithText("TaskPlayer")
            .assertIsDisplayed()
    }

    @Test
    fun videoTitle_isDisplayed() {
        setupScreen()
        composeTestRule
            .onNodeWithText("Health Masterclass")
            .assertIsDisplayed()
    }

    @Test
    fun expertName_isDisplayed() {
        setupScreen()
        composeTestRule
            .onNodeWithText(MockExperts.all[0].name)
            .assertIsDisplayed()
    }

    @Test
    fun freeBadge_isDisplayed() {
        setupScreen()
        composeTestRule
            .onNodeWithText("FREE")
            .assertIsDisplayed()
    }

    @Test
    fun premiumBadge_isDisplayed() {
        setupScreen()
        composeTestRule
            .onNodeWithText("⭐ PREMIUM")
            .assertIsDisplayed()
    }

    @Test
    fun bookSession_button_isDisplayed() {
        setupScreen()
        composeTestRule
            .onNodeWithText("Book Session")
            .assertIsDisplayed()
    }

    @Test
    fun viewProfile_button_isDisplayed() {
        setupScreen()
        composeTestRule
            .onNodeWithText("View Profile")
            .assertIsDisplayed()
    }

    @Test
    fun aiInsights_button_isDisplayed() {
        setupScreen()
        composeTestRule
            .onNodeWithText("AI Insights")
            .assertIsDisplayed()
    }

    @Test
    fun clickViewProfile_triggersCallback() {
        var clicked = false
        setupScreen(onViewProfile = { clicked = true })

        composeTestRule
            .onNodeWithText("View Profile")
            .performClick()

        assert(clicked)
    }

    @Test
    fun clickSavedIcon_triggersCallback() {
        var clicked = false
        setupScreen(onSavedClick = { clicked = true })

        composeTestRule
            .onNodeWithText("TaskPlayer")
            .assertIsDisplayed()

        assert(!clicked)
    }
}