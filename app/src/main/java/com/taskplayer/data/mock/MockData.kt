package com.taskplayer.data.mock

import com.taskplayer.data.model.AccessType
import com.taskplayer.data.model.Expert
import com.taskplayer.data.model.Video

object MockData {

    val experts = listOf(
        Expert(
            id = "e1",
            name = "Dr. Sarah Mitchell",
            title = "Nutrition & Wellness Expert",
            bio = "Dr. Sarah Mitchell is a certified nutritionist with over 12 years " +
                    "of experience helping people transform their health through " +
                    "science-based dietary strategies. She has worked with 500+ clients worldwide.",
            photoUrl = "https://i.pravatar.cc/300?img=47",
            followers = 12400,
            rating = 4.9f,
            totalSessions = 530
        ),
        Expert(
            id = "e2",
            name = "Marcus Reid",
            title = "High Performance Coach",
            bio = "Marcus is a former professional athlete turned performance coach. " +
                    "He specializes in building mental toughness, peak physical " +
                    "conditioning, and elite daily routines.",
            photoUrl = "https://i.pravatar.cc/300?img=12",
            followers = 8900,
            rating = 4.8f,
            totalSessions = 320
        ),
        Expert(
            id = "e3",
            name = "Dr. Leila Ahmadi",
            title = "Clinical Psychologist",
            bio = "Dr. Leila Ahmadi is a clinical psychologist specializing in anxiety, " +
                    "burnout, and cognitive behavioral therapy. She brings a calm, " +
                    "evidence-based approach to mental wellness.",
            photoUrl = "https://i.pravatar.cc/300?img=45",
            followers = 15600,
            rating = 4.95f,
            totalSessions = 780
        ),
        Expert(
            id = "e4",
            name = "James Owusu",
            title = "Business Strategy Advisor",
            bio = "James is a seasoned entrepreneur and business advisor who has helped " +
                    "launch over 30 startups. He focuses on lean strategy, " +
                    "product-market fit, and building scalable systems.",
            photoUrl = "https://i.pravatar.cc/300?img=15",
            followers = 6700,
            rating = 4.7f,
            totalSessions = 210
        ),
        Expert(
            id = "e5",
            name = "Emma Lawson",
            title = "Mindfulness & Meditation Guide",
            bio = "Emma has been teaching mindfulness and meditation for 8 years. " +
                    "Her sessions blend ancient techniques with modern neuroscience " +
                    "to help people find clarity and reduce stress.",
            photoUrl = "https://i.pravatar.cc/300?img=44",
            followers = 21000,
            rating = 4.95f,
            totalSessions = 950
        )
    )

    val videos = listOf(
        Video(
            id = "v1",
            videoTitle = "The Truth About Eating Clean",
            description = "Discover what eating clean actually means and how small daily " +
                    "choices compound into massive health improvements over time.",
            expert = experts[0],
            accessType = AccessType.FREE,
            videoUrl = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerBlazes.mp4",
            thumbnailUrl = "https://images.pexels.com/photos/1640777/pexels-photo-1640777.jpeg",
            price = 0.0,
            tags = listOf("Nutrition", "Health", "Lifestyle", "Diet"),
            duration = "12:34",
            views = "24K"
        ),
        Video(
            id = "v2",
            videoTitle = "Morning Routine of High Performers",
            description = "The first 90 minutes of your morning define the entire day. " +
                    "Learn the exact morning framework used by world-class athletes.",
            expert = experts[1],
            accessType = AccessType.PREMIUM,
            videoUrl = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ElephantsDream.mp4",
            thumbnailUrl = "https://images.pexels.com/photos/1552242/pexels-photo-1552242.jpeg",
            price = 9.99,
            tags = listOf("Performance", "Morning Routine", "Productivity", "Mindset"),
            duration = "18:45",
            views = "41K"
        ),
        Video(
            id = "v3",
            videoTitle = "Breaking the Anxiety Loop",
            description = "Understand the neurological basis of anxiety and learn 3 " +
                    "clinically proven CBT techniques to interrupt the anxiety cycle.",
            expert = experts[2],
            accessType = AccessType.FREE,
            videoUrl = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4",
            thumbnailUrl = "https://images.pexels.com/photos/3807517/pexels-photo-3807517.jpeg",
            price = 0.0,
            tags = listOf("Psychology", "Anxiety", "CBT", "Mental Health"),
            duration = "22:10",
            views = "67K"
        ),
        Video(
            id = "v4",
            videoTitle = "From Idea to Product in 30 Days",
            description = "A step-by-step framework for validating, building, and launching " +
                    "your first product without wasting time or money.",
            expert = experts[3],
            accessType = AccessType.PREMIUM,
            videoUrl = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/SubaruOutbackOnStreetAndDirt.mp4",
            thumbnailUrl = "https://images.pexels.com/photos/3184465/pexels-photo-3184465.jpeg",
            price = 14.99,
            tags = listOf("Business", "Startup", "Product", "Strategy"),
            duration = "31:05",
            views = "18K"
        ),
        Video(
            id = "v5",
            videoTitle = "5-Minute Mindfulness Reset",
            description = "When your mind is overwhelmed, this guided 5-minute practice " +
                    "will bring you back to center. No experience required.",
            expert = experts[4],
            accessType = AccessType.FREE,
            videoUrl = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerEscapes.mp4",
            thumbnailUrl = "https://images.pexels.com/photos/3822622/pexels-photo-3822622.jpeg",
            price = 0.0,
            tags = listOf("Mindfulness", "Meditation", "Stress", "Breathing"),
            duration = "5:00",
            views = "89K"
        ),
        Video(
            id = "v6",
            videoTitle = "The Science of Deep Sleep",
            description = "Sleep is your most powerful recovery tool. Learn the 4 sleep " +
                    "phases and how to optimize each one for full restoration.",
            expert = experts[0],
            accessType = AccessType.PREMIUM,
            videoUrl = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/TearsOfSteel.mp4",
            thumbnailUrl = "https://images.pexels.com/photos/1028741/pexels-photo-1028741.jpeg",
            price = 9.99,
            tags = listOf("Sleep", "Recovery", "Health", "Neuroscience"),
            duration = "27:20",
            views = "33K"
        )
    )
}