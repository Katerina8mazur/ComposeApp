package com.example.composeapp.domain.repositories

import com.example.composeapp.domain.models.Article
import com.example.composeapp.domain.models.ArticlesList

interface ArticlesRepository {

    suspend fun getAllArticles(limit: Int): ArticlesList

    suspend fun getArticle(id: Int): Article
}