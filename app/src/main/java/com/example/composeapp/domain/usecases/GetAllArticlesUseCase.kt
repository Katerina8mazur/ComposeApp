package com.example.composeapp.domain.usecases

import com.example.composeapp.domain.models.ArticlesList
import com.example.composeapp.domain.repositories.ArticlesRepository
import javax.inject.Inject

class GetAllArticlesUseCase @Inject constructor(private val repository: ArticlesRepository) {
    suspend fun execute(limit: Int): ArticlesList {
        return repository.getAllArticles(limit)
    }
}