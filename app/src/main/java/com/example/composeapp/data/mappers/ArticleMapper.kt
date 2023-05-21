package com.example.composeapp.data.mappers

import com.example.composeapp.data.models.ArticleResponse
import com.example.composeapp.data.models.ArticlesListResponse
import com.example.composeapp.domain.models.Article
import com.example.composeapp.domain.models.ArticlesList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList
import javax.inject.Inject

class ArticleMapper @Inject constructor() {

    fun map(item: ArticleResponse?): Article {
        return item?.let { response ->
            with(response) {
                Article(
                    id = id ?: 0,
                    title = title ?: "",
                    url = url ?: "",
                    imageUrl = imageUrl ?: "",
                    newsSite = newsSite ?: "",
                    summary = summary ?: "",
                    publishedAt = toNormalDate(publishedAt) ?: ""
                )
            }
        } ?: Article(
            id = 0,
            title = "",
            url = "",
            imageUrl = "",
            newsSite = "",
            summary = "",
            publishedAt = ""
        )
    }

    fun map(item: ArticlesListResponse?): ArticlesList {
        return item?.let { response ->
            with(response){
                ArticlesList(
                    count = count ?: 0,
                    next = next ?: "",
                    previous = previous ?: "",
                    results = results?.map {
                        map(it)
                    }?.toPersistentList() ?: persistentListOf()
                )
            }
        } ?: ArticlesList(
            count = 0,
            next = "",
            previous = "",
            results = persistentListOf()
        )
    }

    fun toNormalDate(string: String?): String? {
        return string?.replace('T', ' ')?.replace('Z', ' ')
    }
}