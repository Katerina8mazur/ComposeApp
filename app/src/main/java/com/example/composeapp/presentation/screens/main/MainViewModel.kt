package com.example.composeapp.presentation.screens.main

import androidx.compose.runtime.Immutable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.composeapp.domain.models.ArticlesList
import com.example.composeapp.domain.usecases.GetAllArticlesUseCase
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

@Immutable
data class MainViewState(
    val articlesList: ArticlesList = ArticlesList(
        count = 0,
        next = "",
        previous = "",
        results = persistentListOf()
    ),
    val loading: Boolean = true
)

sealed interface MainEvent {

    object OnStart : MainEvent
    data class OnArticleClick(val id: Int) : MainEvent
}

sealed interface MainAction {

    data class Navigate(val id: Int) : MainAction
}

class MainViewModel(private val getAllArticlesUseCase: GetAllArticlesUseCase) : ViewModel() {

    private val _state = MutableStateFlow(MainViewState())
    val state: StateFlow<MainViewState>
        get() = _state.asStateFlow()

    private val _action = MutableSharedFlow<MainAction?>()
    val action: SharedFlow<MainAction?>
        get() = _action.asSharedFlow()

    fun event(mainEvent: MainEvent) {
        when (mainEvent) {
            is MainEvent.OnStart -> onStart()
            is MainEvent.OnArticleClick -> onArticleClick(id = mainEvent.id)
        }
    }

    private fun onStart() {
        viewModelScope.launch {
            kotlin.runCatching {
                val articlesList = getAllArticlesUseCase.execute(50)
                _state.emit(
                    _state.value.copy(
                        articlesList = articlesList,
                        loading = false
                    )
                )
            }
        }
    }

    private fun onArticleClick(id: Int) {
        viewModelScope.launch {
            _action.emit(MainAction.Navigate(id))
        }
    }
}