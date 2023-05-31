package com.example.composeapp.di

import com.example.composeapp.domain.usecases.GetAllArticlesUseCase
import com.example.composeapp.domain.usecases.GetArticleByIdUseCase
import com.example.composeapp.presentation.screens.article.ArticleViewModel
import com.example.composeapp.presentation.screens.main.MainViewModel
import dagger.Module
import dagger.Provides

@Module
class PresentationModule {

    @Provides
    fun provideMainViewModel(getAllArticlesUseCase: GetAllArticlesUseCase): MainViewModel
        = MainViewModel(getAllArticlesUseCase)

    @Provides
    fun provideArticleViewModel(getArticleByIdUseCase: GetArticleByIdUseCase): ArticleViewModel
            = ArticleViewModel(getArticleByIdUseCase)
}
