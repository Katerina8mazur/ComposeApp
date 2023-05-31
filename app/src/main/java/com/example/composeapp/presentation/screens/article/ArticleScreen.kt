package com.example.composeapp.presentation.screens.article

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.composeapp.R
import com.example.composeapp.presentation.ui.theme.Typography

@Composable
fun ArticleScreen(
    navController: NavController,
    viewModel: ArticleViewModel,
    id: Int
) {
    val state = viewModel.state.collectAsStateWithLifecycle()

    viewModel.event(ArticleEvent.OnStart(id))

    ArticleContent(
        viewState = state.value,
        eventHandler = viewModel::event
    )
}

@Composable
fun ArticleContent(
    viewState: ArticleViewState,
    eventHandler: (ArticleEvent) -> Unit
) {
    if (!viewState.loading)
        Column(
            modifier = Modifier.fillMaxSize().padding(15.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AsyncImage(
                model = viewState.article?.imageUrl,
                contentDescription = "",
            )
            Text(
                text = viewState.article?.title ?: "",
                style = Typography.h5,
                textAlign = TextAlign.Center
            )
            Text(
                text = viewState.article?.newsSite ?: "",
                textAlign = TextAlign.End,
                style = Typography.subtitle1,
                modifier = Modifier
                    .fillMaxWidth()
            )
            Spacer(modifier = Modifier.padding(15.dp))
            Text(
                text = viewState.article?.summary ?: "",
                textAlign = TextAlign.Start,
                style = Typography.body1
            )
            Spacer(modifier = Modifier.padding(15.dp))
            Text(
                text = viewState.article?.publishedAt ?: "",
                textAlign = TextAlign.End,
                style = Typography.subtitle2,
                modifier = Modifier
                    .fillMaxWidth()
            )
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