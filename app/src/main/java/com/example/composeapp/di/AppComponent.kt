package com.example.composeapp.di

import com.example.composeapp.presentation.screens.article.ArticleViewModel
import com.example.composeapp.presentation.screens.main.MainViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [DataModule::class, PresentationModule::class, AppBindsModule::class])
interface AppComponent {
    fun getMainViewModel(): MainViewModel

    fun getArticleViewModel(): ArticleViewModel
}