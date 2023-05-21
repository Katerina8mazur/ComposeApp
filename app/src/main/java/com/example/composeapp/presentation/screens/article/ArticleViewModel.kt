package com.example.composeapp.presentation.screens.article

import androidx.compose.runtime.Immutable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.composeapp.domain.models.Article
import com.example.composeapp.domain.usecases.GetArticleByIdUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@Immutable
data class ArticleViewState(
    val article: Article? = null,
    val loading: Boolean = true
)

sealed interface ArticleEvent {

    data class OnStart(val id: Int) : ArticleEvent
}

class ArticleViewModel(private val getArticleByIdUseCase: GetArticleByIdUseCase) : ViewModel() {

    private val _state = MutableStateFlow(ArticleViewState())
    val state: StateFlow<ArticleViewState>
        get() = _state.asStateFlow()

    fun event(articleEvent: ArticleEvent) {
        when (articleEvent) {
            is ArticleEvent.OnStart -> onStart(articleEvent.id)
        }
    }

    private fun onStart(id: Int) {
        viewModelScope.launch {
            kotlin.runCatching {
                val article = getArticleByIdUseCase.execute(id)
                _state.emit(
                    _state.value.copy(
                        article = article,
                        loading = false
                    )
                )
            }
        }
    }
}