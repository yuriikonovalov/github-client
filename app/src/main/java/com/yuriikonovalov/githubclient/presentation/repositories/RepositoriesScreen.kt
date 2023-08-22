package com.yuriikonovalov.githubclient.presentation.repositories

import android.util.Log
import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.yuriikonovalov.githubclient.R
import com.yuriikonovalov.githubclient.domain.entities.Repository
import com.yuriikonovalov.githubclient.presentation.common.Divider
import com.yuriikonovalov.githubclient.presentation.common.LoadingInitialDataError
import com.yuriikonovalov.githubclient.presentation.common.LoadingInitialDataIndicator
import com.yuriikonovalov.githubclient.presentation.common.LoadingNextPageError
import com.yuriikonovalov.githubclient.presentation.common.LoadingNextPageIndicator
import com.yuriikonovalov.githubclient.presentation.common.RefreshingDataIndicator

@Composable
fun RepositoriesScreen(
    login: String,
    onBackClick: () -> Unit,
    viewModel: RepositoriesScreenViewModel = hiltViewModel(),
) {
    val repositories = viewModel.repositories.collectAsLazyPagingItems()

    RepositoriesScreenContent(
        modifier = Modifier.fillMaxSize(),
        login = login,
        repositories = repositories,
        onBackClick = onBackClick
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun RepositoriesScreenContent(
    modifier: Modifier,
    login: String,
    repositories: LazyPagingItems<Repository>,
    onBackClick: () -> Unit
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopBar(
                modifier = Modifier.fillMaxWidth(),
                scrollBehavior = scrollBehavior,
                login = login,
                onBackClick = onBackClick,
            )
        },
    ) { innerPadding ->
        Log.d("ListLog", innerPadding.toString())
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = innerPadding,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            RefreshingDataIndicator(
                modifier = Modifier.fillMaxWidth(),
                // Visible when:
                // 1. ongoing refreshing from remote;
                // 2. cache items are displayed.
                visible = repositories.loadState.refresh is LoadState.Loading && repositories.itemCount > 0
            )

            items(repositories.itemCount) { index ->
                val repository = repositories[index]
                if (repository != null)
                    RepositoryListItem(
                        modifier = Modifier.fillMaxWidth(),
                        name = repository.name.value,
                        description = repository.description.value,
                        language = repository.language.value,
                        stars = repository.stars.count,
                        forks = repository.forks.count,
                        updated = repository.updatedAt.toUiString()
                    )
            }

            LoadingNextPageIndicator(
                modifier = Modifier.fillMaxWidth(),
                // Visible when: a next page is being loaded.
                visible = repositories.loadState.append is LoadState.Loading,
                textResId = R.string.repositories_screen_loading_next_page
            )

            LoadingNextPageError(
                modifier = Modifier.fillMaxWidth(),
                visible = repositories.loadState.append is LoadState.Error,
                message = if (repositories.loadState.append is LoadState.Error) {
                    (repositories.loadState.append as LoadState.Error).error.message
                } else {
                    null
                },
                onRetryClick = repositories::refresh
            )

            LoadingInitialDataIndicator(
                visible = repositories.loadState.refresh is LoadState.Loading && repositories.itemCount == 0
            )

            LoadingInitialDataError(
                visible = repositories.loadState.refresh is LoadState.Error && repositories.itemCount == 0,
                message = if (repositories.loadState.refresh is LoadState.Error && repositories.itemCount == 0) {
                    (repositories.loadState.refresh as LoadState.Error).error.message
                } else {
                    null
                },
                onRetryClick = repositories::refresh
            )

        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TopBar(
    modifier: Modifier,
    login: String,
    onBackClick: () -> Unit,
    scrollBehavior: TopAppBarScrollBehavior
) {
    Column(modifier = modifier) {
        TopAppBar(
            modifier = Modifier.fillMaxWidth(),
            scrollBehavior = scrollBehavior,
            navigationIcon = {
                IconButton(onClick = onBackClick) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = stringResource(R.string.cd_navigate_back)
                    )
                }
            },
            title = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(
                        text = login,
                        style = MaterialTheme.typography.bodyLarge,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        text = stringResource(R.string.repositories_screen_title),
                        style = MaterialTheme.typography.titleMedium,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        )

        Divider()
    }

}

@Composable
private fun RepositoryListItem(
    modifier: Modifier,
    name: String,
    description: String?,
    language: String?,
    stars: Int,
    forks: Int,
    updated: String
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            modifier = Modifier.padding(horizontal = 16.dp),
            text = name,
            style = MaterialTheme.typography.titleMedium
        )
        if (description != null) {
            Text(
                modifier = Modifier.padding(horizontal = 16.dp),
                text = description,
                style = MaterialTheme.typography.bodyMedium
            )
        }

        RepositoryMetrics(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            language = language,
            stars = stars,
            forks = forks,
            updated = updated
        )

        Divider()
    }
}

@Composable
private fun RepositoryMetrics(
    modifier: Modifier,
    language: String?,
    stars: Int,
    forks: Int,
    updated: String
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (language != null) {
            LanguageMetric(language = language)
        }

        MetricWithIcon(
            text = stars.toString(),
            iconResId = R.drawable.ic_star,
            iconColor = Color.Yellow
        )

        MetricWithIcon(
            text = forks.toString(),
            iconResId = R.drawable.ic_fork,
            iconColor = Color.Gray
        )

        Text(
            modifier = Modifier.weight(1f),
            text = updated,
            style = MaterialTheme.typography.bodySmall,
            textAlign = TextAlign.End
        )
    }
}

@Composable
fun LanguageMetric(
    language: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        shape = MaterialTheme.shapes.small
    ) {
        Text(
            modifier = Modifier.padding(4.dp),
            text = language,
            style = MaterialTheme.typography.bodySmall
        )
    }
}

@Composable
fun MetricWithIcon(
    text: String,
    @DrawableRes iconResId: Int,
    iconColor: Color,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Icon(
            modifier = Modifier.size(18.dp),
            painter = painterResource(id = iconResId),
            contentDescription = "",
            tint = iconColor
        )
        Text(
            text = text,
            style = MaterialTheme.typography.bodySmall
        )
    }
}