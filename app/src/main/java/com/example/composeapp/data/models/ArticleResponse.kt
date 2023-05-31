package com.example.composeapp.data.models

import com.google.gson.annotations.SerializedName


data class ArticleResponse(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("title")
    val title: String?,
    @SerializedName("url")
    val url: String?,
    @SerializedName("image_url")
    val imageUrl: String?,
    @SerializedName("news_site")
    val newsSite: String?,
    @SerializedName("summary")
    val summary: String?,
    @SerializedName("published_at")
    val publishedAt: String?
)