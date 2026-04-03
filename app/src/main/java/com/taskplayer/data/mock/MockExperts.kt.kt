package com.taskplayer.data.mock

import com.taskplayer.data.model.Expert

object MockExperts {

    val all = listOf(
        Expert(
            id            = "e1",
            name          = "Dr. Sarah Mitchell",
            title         = "Nutrition & Wellness Expert",
            bio           = "Dr. Sarah Mitchell is a certified nutritionist with over 12 years of experience helping people transform their health through science-based dietary strategies.",
            photoUrl      = "https://i.pravatar.cc/300?img=47",
            followers     = 12400,
            rating        = 4.9f,
            totalSessions = 530
        ),
        Expert(
            id            = "e2",
            name          = "Marcus Reid",
            title         = "High Performance Coach",
            bio           = "Marcus is a former professional athlete turned performance coach. He specializes in building mental toughness, peak physical conditioning, and elite daily routines.",
            photoUrl      = "https://i.pravatar.cc/300?img=12",
            followers     = 8900,
            rating        = 4.8f,
            totalSessions = 320
        ),
        Expert(
            id            = "e3",
            name          = "Dr. Leila Ahmadi",
            title         = "Clinical Psychologist",
            bio           = "Dr. Leila Ahmadi specializes in anxiety, burnout, and cognitive behavioral therapy with a calm, evidence-based approach to mental wellness.",
            photoUrl      = "https://i.pravatar.cc/300?img=45",
            followers     = 15600,
            rating        = 4.95f,
            totalSessions = 780
        ),
        Expert(
            id            = "e4",
            name          = "James Owusu",
            title         = "Business Strategy Advisor",
            bio           = "James is a seasoned entrepreneur who has helped launch over 30 startups. He focuses on lean strategy, product-market fit, and scalable systems.",
            photoUrl      = "https://i.pravatar.cc/300?img=15",
            followers     = 6700,
            rating        = 4.7f,
            totalSessions = 210
        ),
        Expert(
            id            = "e5",
            name          = "Emma Lawson",
            title         = "Mindfulness & Meditation Guide",
            bio           = "Emma blends ancient mindfulness techniques with modern neuroscience to help people find clarity and reduce stress in everyday life.",
            photoUrl      = "https://i.pravatar.cc/300?img=44",
            followers     = 21000,
            rating        = 4.95f,
            totalSessions = 950
        ),
        Expert(
            id            = "e6",
            name          = "Daniel Park",
            title         = "Personal Finance Expert",
            bio           = "Daniel helps everyday people build wealth through simple, proven financial strategies. Former Wall Street analyst turned financial educator.",
            photoUrl      = "https://i.pravatar.cc/300?img=33",
            followers     = 9800,
            rating        = 4.85f,
            totalSessions = 410
        )
    )

    fun forCategory(index: Int) = all[index % all.size]
}