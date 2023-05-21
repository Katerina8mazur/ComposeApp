package com.example.composeapp.data.services

import com.example.composeapp.data.models.ArticleResponse
import com.example.composeapp.data.models.ArticlesListResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ArticlesApiService {

    @GET("articles")
    suspend fun getAllArticles(@Query("limit") limit: Int): ArticlesListResponse

    @GET("articles/{id}")
    suspend fun getArticle(@Path("id") id: Int): ArticleResponse
}