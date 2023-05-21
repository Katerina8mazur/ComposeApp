package com.example.composeapp.presentation.screens.main

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.composeapp.R
import com.example.composeapp.domain.models.Article
import com.example.composeapp.presentation.Screen
import com.example.composeapp.presentation.ui.theme.Typography

@Composable
fun MainScreen(
    navController: NavController,
    viewModel: MainViewModel
) {
    val state = viewModel.state.collectAsStateWithLifecycle()
    val action = viewModel.action.collectAsStateWithLifecycle(null)

    viewModel.event(MainEvent.OnStart)

    MainContent(
        viewState = state.value,
        eventHandler = viewModel::event
    )

    MainScreenActions(
        navController = navController,
        viewAction = action.value
    )
}

@Composable
fun MainScreenActions(
    navController: NavController,
    viewAction: MainAction?
) {
    LaunchedEffect(viewAction) {
        when (viewAction) {
            null -> Unit
            is MainAction.Navigate -> {
                navController.navigate(Screen.Article(viewAction.id).route)
            }
        }
    }
}

@Composable
fun MainContent(
    viewState: MainViewState,
    eventHandler: (MainEvent) -> Unit
) {
    if (!viewState.loading)
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(15.dp)
        ) {
            items(
                items = viewState.articlesList.results
            ) {
                ArticleItem(it) {
                    eventHandler(MainEvent.OnArticleClick(it))
                }
                Spacer(
                    modifier = Modifier.padding(5.dp)
                )
            }
        }
    else
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = stringResource(R.string.loading),
                style = Typography.h5
            )
        }
}

@Composable
fun ArticleItem(
    article: Article,
    onClick: (Int) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .border(
                width = 1.dp,
                color = Color.LightGray
            )
            .clickable { onClick.invoke(article.id) }
            .padding(10.dp)
    ) {
        AsyncImage(
            model = article.imageUrl,
            contentDescription = "",
        )
        Text(
            text = article.title,
            style = Typography.h6
        )
        Text(
            text = article.newsSite,
            textAlign = TextAlign.End,
            style = Typography.body2,
            modifier = Modifier
                .fillMaxWidth()
        )
    }
}