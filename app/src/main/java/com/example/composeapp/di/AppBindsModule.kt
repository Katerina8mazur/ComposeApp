package com.example.composeapp.di

import com.example.composeapp.data.repositories.ArticlesRepositoryImpl
import com.example.composeapp.domain.repositories.ArticlesRepository
import dagger.Binds
import dagger.Module

@Module
abstract class AppBindsModule {

    @Binds
    abstract fun bindArticlesRepository(repositoryImpl: ArticlesRepositoryImpl): ArticlesRepository
}