package com.taskplayer.data.remote

import com.google.gson.annotations.SerializedName

data class PexelsVideoResponse(
    @SerializedName("videos") val videos: List<PexelsVideo> = emptyList()
)

data class PexelsVideo(
    @SerializedName("id")          val id: Int                           = 0,
    @SerializedName("duration")    val duration: Int                     = 0,
    @SerializedName("user")        val user: PexelsUser,
    @SerializedName("video_files") val videoFiles: List<PexelsVideoFile> = emptyList(),
    @SerializedName("image")       val thumbnail: String                 = ""
)

data class PexelsUser(
    @SerializedName("name") val name: String = ""
)

data class PexelsVideoFile(
    @SerializedName("quality")   val quality: String  = "",
    @SerializedName("file_type") val fileType: String = "",
    @SerializedName("width")     val width: Int?      = null,
    @SerializedName("height")    val height: Int?     = null,
    @SerializedName("link")      val link: String     = ""
)