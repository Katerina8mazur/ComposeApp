package com.example.composeapp.data.repositories

import com.example.composeapp.data.mappers.ArticleMapper
import com.example.composeapp.data.services.ArticlesApiService
import com.example.composeapp.domain.models.Article
import com.example.composeapp.domain.models.ArticlesList
import com.example.composeapp.domain.repositories.ArticlesRepository
import javax.inject.Inject

class ArticlesRepositoryImpl @Inject constructor(
    private val service: ArticlesApiService,
    private val mapper: ArticleMapper,
) : ArticlesRepository {

    override suspend fun getAllArticles(limit: Int): ArticlesList {
        return mapper.map(service.getAllArticles(limit))
    }

    override suspend fun getArticle(id: Int): Article {
        return mapper.map(service.getArticle(id))
    }
}