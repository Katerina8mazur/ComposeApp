package com.example.composeapp.data.models
import com.google.gson.annotations.SerializedName


data class ArticlesListResponse(
    @SerializedName("count")
    val count: Int?,
    @SerializedName("next")
    val next: String?,
    @SerializedName("previous")
    val previous: String?,
    @SerializedName("results")
    val results: List<ArticleResponse?>?
)