package com.example.composeapp.domain.usecases

import com.example.composeapp.domain.models.Article
import com.example.composeapp.domain.repositories.ArticlesRepository
import javax.inject.Inject

class GetArticleByIdUseCase @Inject constructor(private val repository: ArticlesRepository) {
    suspend fun execute(id: Int): Article {
        return repository.getArticle(id)
    }
}