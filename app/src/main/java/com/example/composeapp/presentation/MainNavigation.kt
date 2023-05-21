package com.example.composeapp.presentation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.composeapp.di.DaggerAppComponent
import com.example.composeapp.di.daggerViewModel
import com.example.composeapp.presentation.screens.article.ArticleScreen
import com.example.composeapp.presentation.screens.main.MainScreen

sealed class Screen(
    val route: String
) {
    object Main : Screen(
        route = "main"
    )

    data class Article(private val id: Int) : Screen(
        route = "article/$id"
    )
}

@Composable
fun MainNavHost(
    navController: NavHostController = rememberNavController(),
    startDestination: Screen = Screen.Main
) {
    val component = DaggerAppComponent.builder().build()
    Scaffold {
        NavHost(
            navController = navController,
            startDestination = startDestination.route,
            modifier = Modifier.padding(it)
        ) {
            composable("main") {
                val viewModel = daggerViewModel {
                    component.getMainViewModel()
                }
                MainScreen(
                    navController = navController,
                    viewModel = viewModel
                )
            }
            composable("article/{id}") {
                val viewModel = daggerViewModel {
                    component.getArticleViewModel()
                }
                ArticleScreen(
                    navController = navController,
                    viewModel = viewModel,
                    id = it.arguments?.getString("id")?.toIntOrNull() ?: 0
                )
            }
        }
    }
}