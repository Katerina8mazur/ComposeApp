package com.example.composeapp.domain.models

import androidx.compose.runtime.Immutable
import kotlinx.collections.immutable.PersistentList

@Immutable
data class ArticlesList(
    val count: Int,
    val next: String,
    val previous: Any,
    val results: PersistentList<Article>
)